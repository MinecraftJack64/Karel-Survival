package com.karel.game.gridobjects.gridentities.zombies.stripper;

import com.karel.game.ArmorShield;
import com.karel.game.Greenfoot;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.shields.ShieldID;

/**
 * Write a description of class TorpedoZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StripperZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.tank, ZombieClass.barrager};
    private static final int gunReloadTime = 30, maxAmmo = 20;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    private int ammo = 10;

    private int strippedCooldown = 0;
    private int robeHealth = 2000;

    private ShieldID shield = new ShieldID(this);
    private boolean disabled = false;

    public String getStaticTextureURL(){return "stripperzareln.png";}
    private static double attackrange = 100;
    public StripperZombie()
    {
        reloadDelayCount = ammo = 0;
        setSpeed(1);
        startHealth(200);
        applyShield(new ArmorShield(shield, robeHealth));
    }
    public void behave()
    {
        if(disabled){
            fire();
            super.behave();
            return;
        }
        double monangle = face(getTarget(), canMove());
        if(hasShield(shield)){
            reloadDelayCount+=getReloadMultiplier();
            if(reloadDelayCount>=gunReloadTime){
                reloadDelayCount = 0;
                heal(this, 50);
                if(ammo<maxAmmo)ammo++;
            }
        }
        if(strippedCooldown>0){
            strippedCooldown--;
            if(strippedCooldown==0){
                applyShield(new ArmorShield(shield, robeHealth));
                setImage("stripperzareln.png");
            }
        }else if(!hasShield(shield)){
            // robe broken
            setImage("nakedstripperzareln.png");
            setSpeed(2);
            disabled = true;
        }
        if(distanceTo(getTarget())>attackrange&&strippedCooldown==0)walk(monangle, 1);
        else if(hasShield(shield)){
            if(ammo>=1){ // need one ammo at least to shoot
                // strip
                robeHealth = getShield(shield).getHealth();
                removeShield(shield);
                setImage("strippedstripperzareln.png");
                strippedCooldown = 60;
            }
        }
        else{
            fire();
        }
    }
    private void fire() 
    {
        if (ammo>0&&canAttack()){
            ZNakedMissile bullet = new ZNakedMissile (Greenfoot.getRandomNumber(360), this);
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("gunshoot");
            ammo--;
            reloadDelayCount = 0;
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
        return "Stripper Zombie";
    }
    @Override
    public String getZombieID(){
        return "stripper";
    }
}
