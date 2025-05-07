package com.karel.game;
/**
 * Write a description of class SimpleAmmoManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SimpleAmmoManager extends AmmoManager
{
    public SimpleAmmoManager(int reloadDelay, int maxAmmo){
        super(reloadDelay, 1, maxAmmo);
    }
}
