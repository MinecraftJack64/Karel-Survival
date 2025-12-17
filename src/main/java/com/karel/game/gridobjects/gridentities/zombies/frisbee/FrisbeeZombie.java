package com.karel.game.gridobjects.gridentities.zombies.frisbee;

import com.karel.game.GridObject;
import com.karel.game.ProjectileReflectShield;
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
public class FrisbeeZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.controller, ZombieClass.assault};
    private static final int gunReloadTime = 150;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    private ZFrisbee frizz;

    public String getStaticTextureURL(){return "gunzareln.png";}
    private static double attackrange = 420, retreatrange = 350;
    /**
     * Initilise this rocket.
     */
    public FrisbeeZombie()
    {
        reloadDelayCount = 5;
        setSpeed(3);
        startHealth(300);
    }
    public void behave()
    {
        if(frizz!=null&&!frizz.isInWorld()){
            if(frizz.isGrabbed())frizz.letGo();
            frizz = null;
            if(hasShield(new ShieldID(this)))removeShield(new ShieldID(this));
        }
        if(frizz!=null){
            if(!canAttack()&&!canMove()){
                frizz.letGo();
                frizz = null;
                if(hasShield(new ShieldID(this)))removeShield(new ShieldID(this));
            }
        }else{
            reloadDelayCount+=getReloadMultiplier();
            double monangle = face(getTarget(), canMove());
            if(distanceTo(getTarget())>attackrange){
                walk(monangle, 1);
            }
            else if(distanceTo(getTarget())<retreatrange){
                fire();walk(monangle, -1);
            }
            else{
                fire();
            }
        }
    }
    public void notifyPull(GridObject t){
        if(t==this)
            super.notifyPull();
        else{
            if(frizz.isGrabbed())frizz.letGo();
            frizz = null;
            if(hasShield(new ShieldID(this)))removeShield(new ShieldID(this));
        }
    }
    public void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZFrisbee bullet = new ZFrisbee (getRotation(), this);
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("gunshoot");
            bullet.grab(this);
            frizz = bullet;
            applyShield(new ProjectileReflectShield(new ShieldID(this), 100));
            reloadDelayCount = 0;
        }
    }
    public boolean prioritizeTarget(){
        return true;
    }
    @Override
    public int getXP(){
        return 200;
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Frisbee Zombie";
    }
    @Override
    public String getZombieID(){
        return "frisbee";
    }
}
