package com.karel.game.gridobjects.gridentities.zombies.ribbondancer;

import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Write a description of class ShooterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RibbonDancerZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.controller, ZombieClass.ranger};
    private static final int gunReloadTime = 125;         // The minimum delay between firing the gun.
    private static final int stabReloadTime = 15;

    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    private double reloadStab;

    public String getStaticTextureURL(){return "gunzareln.png";}
    private static double attackrange = 500, retreatrange = 450, stabrange = 150;
    /**
     * Initilise this rocket.
     */
    public RibbonDancerZombie()
    {
        reloadDelayCount = 5;
        setSpeed(3);
        startHealth(150);
    }
    public void behave()
    {
        reloadDelayCount+=getReloadMultiplier();
        reloadStab+=getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())<stabrange){
            stab();
            if(distanceTo(getTarget())>30){
                walk(monangle, 1);
            }
        }else if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else if(distanceTo(getTarget())<retreatrange){fire();walk(monangle, -1);}
        else{
            fire();
        }
    }
    public void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZRibbon bullet = new ZRibbon (getRotation(), this);
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    public void stab(){
        if(reloadStab>=stabReloadTime&&canAttack()){
            ZStabBullet bullet = new ZStabBullet (getRotation(), this);
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("gunshoot");
            reloadStab = 0;
        }
    }
    public int getXP(){
        return 200;
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Ribbon Dancer Zombie";
    }
    @Override
    public String getZombieID(){
        return "ribbondancer";
    }
}
