package com.karel.game.gridobjects.gridentities.zombies.supernova;

import com.karel.game.Greenfoot;
import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Write a description of class ShooterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SupernovaZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.ranger, ZombieClass.barrager};
    private static final int gunReloadTime = 90;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    private int criticalHealth = Greenfoot.getRandomNumber(500);

    public String getStaticTextureURL(){return "gunzareln.png";}
    private static double attackrange = 500, retreatrange = 200;
    /**
     * Initilise this rocket.
     */
    public SupernovaZombie()
    {
        reloadDelayCount = 5;
        setSpeed(2);
        startHealth(500);
        scaleTexture(70);
    }
    public void behave()
    {
        reloadDelayCount+=getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else if(distanceTo(getTarget())<retreatrange&&distanceTo(getTarget())>5){fire();walk(monangle, 1.5);damage(this, 2);}
        else{
            fire();
        }
        if(getHealth()<criticalHealth){
            damage(this, 8);
        }
    }
    public void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZDyingStar bullet = new ZDyingStar (getRotation(), this);
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }

    public void die(GridObject killer){
        addObjectHere(new ZSupernova(this));
        super.die(killer);
    }

    @Override
    public int getXP(){
        return 500;
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Supernova Zombie";
    }
    @Override
    public String getZombieID(){
        return "supernova";
    }
}
