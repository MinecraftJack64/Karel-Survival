package com.karel.game;
/**
 * Write a description of class AmmoManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AmmoManager  
{
    private int ammoReloadTime;//time it takes to reload one ammo
    private int maxAmmo;//maximum holdable amount of ammo
    private int ammo;//amount of ammo
    private int ammoReloadDelay;//current cooldown for reloading ammo
    public AmmoManager(int reloadDelay, int ammo, int maxAmmo){
        this.maxAmmo = maxAmmo;
        this.ammoReloadTime = reloadDelay;
        this.ammo = ammo;//starting amt
    }
    public int getMaxAmmoBar(){
        return maxAmmo*ammoReloadTime;
    }
    public int getAmmoBar(){
        return ammo*ammoReloadTime+ammoReloadDelay;
    }
    public void reload(){
        if(ammo<maxAmmo){
            ammoReloadDelay++;
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
