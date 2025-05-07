package com.karel.game;
import java.util.List;

/**
 * Write a description of class HivemindZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HivemindZombie extends Zombie
{
    private static final int gunReloadTime = 150;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    private GreenfootImage rocket = new GreenfootImage("hivezareln.png");    
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private static double standingrange = 500;
    private int bees = 40;
    private int attackphasecount = 0;
    private int attackphasecooldown = 0;
    private boolean attackphasesuper = !(Greenfoot.getRandomNumber(5)>0);
    /**
     * Initilise this rocket.
     */
    public HivemindZombie()
    {
        reloadDelayCount = gunReloadTime;
        rocket.scale(45, 45);
        setImage(rocket);
        setRotation(180);
        setSpeed(2);
        startHealth(300);
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
        if(distanceTo(getTarget())>standingrange)walk(monangle, 1);
        else if(bees<=0){
            super.behave();
        }
        else{
            fire();
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (attackphasecount>0/*finish started attacks*/||(reloadDelayCount>=gunReloadTime&&canAttack())){
            if(attackphasecooldown<=0&&bees>0){
                Zombee bullet = new Zombee(this);
                getWorld().addObject (bullet, getRealX(), getRealY());
                bees--;
                attackphasecount++;
                attackphasecooldown = 3;
            }
            attackphasecooldown--;
            if(attackphasecount>=4||(attackphasecount==3&&!attackphasesuper)||bees<=0){
                reloadDelayCount = 0;
                attackphasecount = 0;
                attackphasecooldown = 0;
                attackphasesuper = !(Greenfoot.getRandomNumber(5)>0);
            }
        }
    }
    //ovveride this
    public int getXP(){
        return 400;
    }
    
    public String getName(){
        return "Hivemind Zombie";
    }
}
