package com.karel.game.trackers;

import com.karel.game.IAmmoManager;

/**
 * Write a description of class AmmoManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AmmoManager implements IAmmoManager
{
    private int ammoReloadTime;//time it takes to reload one ammo
    private int maxAmmo;//maximum holdable amount of ammo
    private int ammo;//amount of ammo
    private double ammoReloadDelay;//current cooldown for reloading ammo
    public AmmoManager(int reloadDelay, int ammo, int maxAmmo){
        this.maxAmmo = maxAmmo;
        this.ammoReloadTime = reloadDelay;
        this.ammo = ammo;//starting amt
    }
    public int getMaxAmmoBar(){
        return maxAmmo*ammoReloadTime;
    }
    public int getAmmoBar(){
        return (int)(ammo*ammoReloadTime+ammoReloadDelay);
    }
    public void reload(){
        reload(1);
    }
    public void reload(double speed){
        if(ammo<maxAmmo){
            ammoReloadDelay+=speed;
            if(ammoReloadDelay>=ammoReloadTime){
                ammo++;
                ammoReloadDelay = 0;
            }
        }
    }
    public int getAmmo(){
        return ammo;
    }
    public int getMaxAmmo(){
        return maxAmmo;
    }
    public void donateAmmo(int amt){
        ammo+=amt;
        handleAmmoOverflow();
    }
    public void donateAmmoBar(int amt){
        ammoReloadDelay+=amt;
        ammo+=ammoReloadDelay/ammoReloadTime;
        ammoReloadDelay = ammoReloadDelay%ammoReloadTime;
        handleAmmoOverflow();
    }
    public void handleAmmoOverflow(){
        if(ammo>=maxAmmo){
            ammo = maxAmmo;
            ammoReloadDelay = 0;
        }
    }
    public boolean hasAmmo(){
        return hasAmmo(1);
    }
    public void useAmmo(){
        useAmmo(1);
    }
    public boolean hasAmmo(int amt){
        return getAmmo()>=amt;
    }
    public void useAmmo(int amt){
        if(ammo>=amt)
        ammo-=amt;
    }
}
