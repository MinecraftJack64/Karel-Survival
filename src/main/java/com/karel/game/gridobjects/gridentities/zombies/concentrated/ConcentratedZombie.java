package com.karel.game.gridobjects.gridentities.zombies.concentrated;

import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Write a description of class ShooterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ConcentratedZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.barrager, ZombieClass.ranger};
    private static final int gunReloadTime = 30*60;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    private boolean attacking;

    public String getStaticTextureURL(){return "zareln.png";}
    private static double attackrange = 800, retreatrange = 500;
    private int knockbacks = 4;
    /**
     * Initilise this rocket.
     */
    public ConcentratedZombie()
    {
        reloadDelayCount = 5;
        setSpeed(3);
        startHealth(1500);
    }
    public void behave()
    {
        if(attacking)reloadDelayCount+=getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        if(!canMove()){
            reloadDelayCount = 0;
            attacking = false;
        }
        if(distanceTo(getTarget())>attackrange&&!attacking)walk(monangle, 1);
        else if(distanceTo(getTarget())<retreatrange&&!attacking){walk(monangle, -1);}
        else{
            fire();
        }
        if(canMove()&&getHealth()<knockbacks*300){
            knockbacks--;
            knockBack(monangle+180, 100, 30, this);
        }
    }
    public void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZConcentratedWave bullet = new ZConcentratedWave(this);
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
            attacking = false;
        }else{
            attacking = true;
        }
    }
    public void notifyPull(){
        super.notifyPull();
        reloadDelayCount = 0;
        attacking = false;
    }
    //ovveride this
    public int getXP(){
        return 200;
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Concentrated Zombie";
    }
    @Override
    public String getZombieID(){
        return "concentrated";
    }
}
