package com.karel.game.trackers;

/**
 * Write a description of class SimpleAmmoManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SimpleAmmoManager extends AmmoManager
{
    public SimpleAmmoManager(int reloadDelay, int ammo){
        super(reloadDelay, ammo, 1);
    }
}
