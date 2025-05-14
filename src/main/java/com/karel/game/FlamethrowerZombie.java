package com.karel.game;
import java.util.List;

/**
 * Write a description of class FlamethrowerZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FlamethrowerZombie extends Zombie
{
    private static final int gunReloadTime = 3;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    public static String getStaticTextureURL(){return "zareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private static double attackrange = 150, retreatrange = 50;
    /**
     * Initilise this rocket.
     */
    public FlamethrowerZombie()
    {
        reloadDelayCount = 5;
        setSpeed(2);
        startHealth(400);
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
        else if(distanceTo(getTarget())<retreatrange){fire();}
        else{
            walk(monangle, 0.5);
            fire();
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            for(int deg = -35; deg<=35; deg+=10){
                Flame mbullet = new Flame(getRealRotation()+deg, this);
                getWorld().addObject (mbullet, getRealX(), getRealY());
            }
            Sounds.play("flame");
            reloadDelayCount = 0;
        }
    }
    //ovveride this
    public int getXP(){
        return 900;
    }
    
    public String getName(){
        return "Firebreather Zombie";
    }
}
