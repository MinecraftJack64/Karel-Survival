package com.karel.game.gridobjects.gridentities.zombies.urchin;

import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.shields.ShieldID;

/**
 * Write a description of class ShooterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UrchinZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.whittler, ZombieClass.pressurer};
    private static final int gunReloadTime = 5;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    private int currentAngle = 0; // +8

    public String getStaticTextureURL(){return "gunzareln.png";}
    private static double attackrange = 600;
    private ShieldID shield = new ShieldID(this);
    /**
     * Initilise this rocket.
     */
    public UrchinZombie()
    {
        reloadDelayCount = 5;
        setSpeed(3);
        startHealth(300);
    }
    public void behave()
    {
        reloadDelayCount+=getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else{
            fire();
            walk(monangle+90, 0.5);
        }
    }
    public void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZUrchinSpine bullet = new ZUrchinSpine (currentAngle, this);
            currentAngle+=8;
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    public void hitIgnoreShield(int amt, double exp, GridObject source){
        if(!hasShield(shield)){
            applyShield(new UrchinShield(shield, 0.8, 60));
        }
        super.hitIgnoreShield(amt, exp, source);
    }
    @Override
    public int getXP(){
        return 400;
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Urchin Zombie";
    }
    @Override
    public String getZombieID(){
        return "urchin";
    }
}
