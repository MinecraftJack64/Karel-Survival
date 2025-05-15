package com.karel.game;
import java.util.List;

/**
 * Write a description of class ShooterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShooterZombie extends Zombie
{
    private static final int gunReloadTime = 75;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    public static String getStaticTextureURL(){return "gunzareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
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

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        reloadDelayCount++;
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        ammo++;
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else if(distanceTo(getTarget())<retreatrange){fire();walk(monangle, -1);}
        else{
            fire();
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
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
    
    public String getName(){
        return "Shooter Zombie";
    }
}
