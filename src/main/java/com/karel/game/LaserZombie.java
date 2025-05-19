package com.karel.game;
import java.util.List;

/**
 * Write a description of class LaserZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LaserZombie extends Zombie
{
    private static final int gunReloadTime = 75;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "laserzareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private static double attackrange = 320, attackrange2 = 150;
    private static double retreatrange = 300, pointblankrange = 50;
    /**
     * Initilise this rocket.
     */
    public LaserZombie()
    {
        reloadDelayCount = 5;
        setSpeed(3.2);
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
        double d = distanceTo(getTarget());
        if(d>attackrange||d<attackrange2&&d>pointblankrange)walk(monangle, 1);
        else if(d<retreatrange&&d>pointblankrange)walk(monangle, -1);
        else{
            fire();
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZBullet bullet = new ZBullet (getRealRotation()+10, this);
            getWorld().addObject (bullet, getRealX(), getRealY());
            ZBullet bullet2 = new ZBullet (getRealRotation()-10, this);
            getWorld().addObject (bullet2, getRealX(), getRealY());
            ZBullet bullet3 = new ZBullet (getRealRotation()+15, this);
            getWorld().addObject (bullet3, getRealX(), getRealY());
            ZBullet bullet4 = new ZBullet (getRealRotation()-15, this);
            getWorld().addObject (bullet4, getRealX(), getRealY());
            //bullet.move ();
            reloadDelayCount = 0;
        }
    }
    //ovveride this
    public int getXP(){
        return 600;
    }
    
    public String getName(){
        return "Laser Zombie";
    }
}
