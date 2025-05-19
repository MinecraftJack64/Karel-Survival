package com.karel.game;
import java.util.List;

/**
 * Write a description of class TorpedoZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TorpedoZombie extends Zombie
{
    private static final int gunReloadTime = 30;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "gunzareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private static double attackrange = 700;
    private ZTorpedo myTorpedo;
    /**
     * Initilise this rocket.
     */
    public TorpedoZombie()
    {
        reloadDelayCount = 0;
        setSpeed(1);
        startHealth(100);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        if(myTorpedo==null||myTorpedo.isDone())reloadDelayCount++;
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
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
            ZTorpedo bullet = new ZTorpedo (getRealRotation(), this);
            getWorld().addObject (bullet, getRealX(), getRealY());
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
            myTorpedo = bullet;
        }
    }
    //ovveride this
    public int getXP(){
        return 1000;
    }
    
    public String getName(){
        return "Torpedo Zombie";
    }
}
