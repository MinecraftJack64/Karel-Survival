package com.karel.game.gridobjects.gridentities.zombies.torpedo;

import com.karel.game.Sounds;
import com.karel.game.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;

/**
 * Write a description of class TorpedoZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TorpedoZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.ranger, ZombieClass.whittler, ZombieClass.pressurer};
    private static final int gunReloadTime = 30;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "gunzareln.png";}
    private static double attackrange = 700;
    private ZTorpedo myTorpedo;
    public TorpedoZombie()
    {
        reloadDelayCount = 0;
        setSpeed(1);
        startHealth(100);
    }
    public void behave()
    {
        if(myTorpedo==null||myTorpedo.isDone())reloadDelayCount+=getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else{
            fire();
        }
    }
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZTorpedo bullet = new ZTorpedo (getRotation(), this);
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
            myTorpedo = bullet;
        }
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 1000;
    }
    
    public String getName(){
        return "Torpedo Zombie";
    }
}
