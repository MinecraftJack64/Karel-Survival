package com.karel.game;
import java.util.List;

/**
 * Write a description of class MarksmanZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MarksmanZombie extends Zombie
{
    private static final int gunReloadTime = 300;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "riflezareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private static double attackrange = 500, retreatrange = 450;
    /**
     * Initilise this rocket.
     */
    public MarksmanZombie()
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
        else if(distanceTo(getTarget())<retreatrange){fire();walk(monangle, -0.75);}
        else{
            reloadDelayCount++;//reload at 150, otherwise at 300
            fire();
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZSBullet bullet = new ZSBullet (getRealRotation(), this);
            getWorld().addObject (bullet, getRealX(), getRealY());
            Sounds.play("marksmanshoot");
            reloadDelayCount = 0;
        }
    }
    //ovveride this
    public int getXP(){
        return 500;
    }
    
    public String getName(){
        return "Marksman Zombie";
    }
}
