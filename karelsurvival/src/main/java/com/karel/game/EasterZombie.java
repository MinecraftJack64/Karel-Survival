package com.karel.game;
import java.util.List;

/**
 * Write a description of class EasterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EasterZombie extends Zombie
{
    private static final int gunReloadTime = 75;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    private GreenfootImage rocket = new GreenfootImage("gunzareln.png");    
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private static double attackrange = 250, retreatrange = 150;
    /**
     * Initilise this rocket.
     */
    public EasterZombie()
    {
        reloadDelayCount = 5;
        rocket.scale(45, 45);
        setImage(rocket);
        setRotation(180);
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
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);//HOP
        else if(distanceTo(getTarget())<retreatrange){fire();walk(monangle, -1);}
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
            ZEasterEgg bullet = new ZEasterEgg (getRealRotation(), this);
            getWorld().addObject (bullet, getRealX(), getRealY());
            //bullet.move ();
            reloadDelayCount = 0;
        }
    }
    //ovveride this
    public int getXP(){
        return 400;
    }
    
    public String getName(){
        return "Easter Zombie";
    }
}
