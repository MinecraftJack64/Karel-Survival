package com.karel.game.gridobjects.gridentities.zombies.hornetneck;

import com.karel.game.GridObject;
import com.karel.game.PercentageShield;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.weapons.ShieldID;

/**
 * Write a description of class HornetNeckZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HornetNeckZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.spawner, ZombieClass.assault};
    private static final int gunReloadTime = 5;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "hornetnestzareln.png";}
    private int bees = 20;
    /**
     * Initilise this rocket.
     */
    public HornetNeckZombie()
    {
        reloadDelayCount = gunReloadTime;
        setSpeed(3);
        startHealth(400);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        reloadDelayCount++;
        super.behave();
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if(bees>0&&canAttack()&&reloadDelayCount>=gunReloadTime){
            Hornet bullet = new Hornet();
            getWorld().addObject (bullet, getX(), getY());
            bullet.applyShield(new PercentageShield(new ShieldID(bullet), 0.6, 30));
            bees--;
            reloadDelayCount = 0;
        }
    }
    
    public void hitIgnoreShield(int amt, double exp, GridObject source){
        fire();
        super.hitIgnoreShield(amt, exp, source);
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 550;
    }
    
    public String getName(){
        return "Hornet-Neck Zombie";
    }
}
