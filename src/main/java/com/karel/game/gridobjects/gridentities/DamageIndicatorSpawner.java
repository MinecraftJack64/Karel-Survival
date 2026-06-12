package com.karel.game.gridobjects.gridentities;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZombieDropper;

/**
 * Write a description of class Turret here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DamageIndicatorSpawner extends GridEntity
{
    public void update(){
        for(int i = 0; i < 1200; i+=5){
            for(int j = 0; j < 800; j+=5){
                spawnZombieAt(new DamageIndicator(), i, j);
            }
        }
        getWorld().removeObject(this);
    }
    public void spawnZombieAt(GridObject z, double x, double y){
        double d = distanceTo(x, y);
        ZombieDropper pack = new ZombieDropper(getAngle(x, y)+90, d, d, z, this);
        getWorld().addObject(pack, getX(), getY());
    }
    public String getName(){
        return "Damage Indicator Field";
    }
}
