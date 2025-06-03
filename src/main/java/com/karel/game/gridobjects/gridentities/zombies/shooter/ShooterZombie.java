package com.karel.game.gridobjects.gridentities.zombies.shooter;

import com.karel.game.Sounds;
import com.karel.game.ZBullet;
import com.karel.game.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;

/**
 * Write a description of class ShooterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShooterZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.ranger};
    private static final int gunReloadTime = 75;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "gunzareln.png";}
    private static double attackrange = 400, retreatrange = 300;
    /**
     * Initilise this rocket.
     */
    public ShooterZombie()
    {
        reloadDelayCount = 5;
        setSpeed(3);
        startHealth(150);
    }
    public void behave()
    {
        reloadDelayCount+=getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else if(distanceTo(getTarget())<retreatrange){fire();walk(monangle, -1);}
        else{
            fire();
        }
    }
    public void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZBullet bullet = new ZBullet (getRealRotation(), this);
            getWorld().addObject (bullet, getRealX(), getRealY());
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    //ovveride this
    public int getXP(){
        return 200;
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Shooter Zombie";
    }
}
