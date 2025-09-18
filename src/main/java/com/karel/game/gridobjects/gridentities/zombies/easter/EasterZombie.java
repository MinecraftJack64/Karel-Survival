package com.karel.game.gridobjects.gridentities.zombies.easter;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Write a description of class EasterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EasterZombie extends Zombie
{
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.spawner, ZombieClass.midranger, ZombieClass.assault};
    private static final int gunReloadTime = 75;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    private int jumpCooldown = 50;    // How long it takes to jump again. // 50

    public String getStaticTextureURL(){return "easterzareln.png";}
    private static double attackrange = 250, retreatrange = 150;
    /**
     * Initilise this rocket.
     */
    public EasterZombie()
    {
        reloadDelayCount = 5;
        setSpeed(2.5);
        startHealth(150);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        reloadDelayCount+=getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>attackrange)attemptHop(monangle);
        else if(distanceTo(getTarget())<retreatrange){fire();walk(monangle, -1);}
        else{
            fire();
        }
    }

    public void attemptHop(double monangle){
        if (canMove()&&jumpCooldown <= 0) {
            initiateJump(monangle, Math.min(50*getMultipliedSpeed()/getSpeed(), distanceTo(getTarget())), 50);
            jumpCooldown = 50;
        }else{
            jumpCooldown--;
            if(isOnGround()){
                walk(monangle, 1);
            }
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZEasterEgg bullet = new ZEasterEgg (getRotation(), this);
            getWorld().addObject (bullet, getX(), getY());
            initiateJump(0, 0, 50);
            reloadDelayCount = 0;
        }
    }
    //ovveride this
    public int getXP(){
        return 400;
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Easter Zombie";
    }
    @Override
    public String getZombieID(){
        return "easter";
    }
}
