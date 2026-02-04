package com.karel.game.gridobjects.gridentities.zombies.minishooter;

import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Write a description of class ShooterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MiniShooterZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.meatshield, ZombieClass.midranger};
    private static final int gunReloadTime = 15;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "gunzareln.png";}
    private static double attackrange = 200, retreatrange = 100;
    /**
     * Initilise this rocket.
     */
    public MiniShooterZombie()
    {
        reloadDelayCount = 5;
        setSpeed(3);
        startHealth(50);
        scaleTexture(20);
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
            ZMiniBullet bullet = new ZMiniBullet (getRotation(), this);
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }

    public void die(GridObject killer){
        addObjectHere(new ZBullet(getRotation(), this));
        super.die(killer);
    }

    @Override
    public int getXP(){
        return 200;
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Shooter Zombie";
    }
    @Override
    public String getZombieID(){
        return "shooter";
    }
}
