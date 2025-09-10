package com.karel.game;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;

/**
 * Write a description of class SpawnableZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpawnableZombie extends Zombie  
{
    public boolean willNotify(){
        return false;
    }
}
