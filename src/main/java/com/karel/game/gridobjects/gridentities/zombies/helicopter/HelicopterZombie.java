package com.karel.game.gridobjects.gridentities.zombies.helicopter;

import com.karel.game.ArmorShield;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.shields.ShieldID;
import com.karel.game.trackers.AmmoManager;

/**
 * Write a description of class ShooterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HelicopterZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.ranger};
    private static final int gunReloadTime = 200;

    private double reloadDelayCount;

    public String getStaticTextureURL(){return "gunzareln.png";}
    private static double attackrange = 600, retreatrange = 500;
    private boolean ground;
    private AmmoManager ammo;
    private static final int targetHeight = 300;
    private static final int ascendSpeed = 10;
    /**
     * Initilise this rocket.
     */
    public HelicopterZombie()
    {
        reloadDelayCount = 5;
        setSpeed(4);
        startHealth(450);
        ground = true;
        ammo = new AmmoManager(60, 8, 8);
    }
    public void behave()
    {
        if(reloadDelayCount<gunReloadTime)reloadDelayCount+=getReloadMultiplier();
        else if(isOnGround())ammo.reload(getReloadMultiplier());
        double monangle = face(getTarget(), canMove());
        if(!ground){
            if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
            else if(distanceTo(getTarget())<retreatrange){fire();walk(monangle, -1);}
            else{
                fire();
            }
            if(ammo.getAmmo()==0){
                ground = true;
                if(!hasShield()){
                    applyShield(new ArmorShield(new ShieldID(this), 350));
                }
            }
        }else if(ammo.getAmmo()==ammo.getMaxAmmo()){
            ground = false;
        }
        if(ground&&getHeight()>0)setHeight(getHeight()-ascendSpeed);
        else if(!ground&&getHeight()<targetHeight)setHeight(getHeight()+ascendSpeed);
    }
    public void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&ammo.hasAmmo()&&canAttack()){
            ZHelicopterBullet bullet = new ZHelicopterBullet (getRotation(), getHeight(), distanceTo(getTarget()), this);
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
            ammo.useAmmo();
        }
    }
    @Override
    public int getXP(){
        return 400;
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Helicopter Zombie";
    }
    @Override
    public String getZombieID(){
        return "helicopter";
    }
    public boolean canFly(){
        return true;
    }
    public boolean isColumnar(){
        return true;
    }
}
