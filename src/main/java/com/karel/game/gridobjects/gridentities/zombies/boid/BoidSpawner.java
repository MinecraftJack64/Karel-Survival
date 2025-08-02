package com.karel.game.gridobjects.gridentities.zombies.boid;

import com.karel.game.ZombiePackage;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.ZombieDropper;
import com.karel.game.Greenfoot;

//

/**
 * Spawns a specified number of BoidZombies at random positions around the spawner.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BoidSpawner extends ZombiePackage
{
    private int count;
    private int reloadDelayCount = 0; // 5
    public BoidSpawner(int count, GridObject source){
        super(source);
        this.count = count;
    }
    public void update(){
        if(reloadDelayCount < 5){
            reloadDelayCount++;
            return;
        }
        reloadDelayCount = 0;
        count--;
        spawnZombieAt(newBoid());
        if(count <= 0){
            getWorld().removeObject(this);
            return;
        }
    }
    public GridObject newBoid(){
        GridEntity z = new BoidZombie();
        return z;
    }
    public void spawnZombie(GridObject z){
        getWorld().addObject(z, getX(), getY());
    }
    public void spawnZombieAt(GridObject z){
        double d = 50;
        ZombieDropper pack = new ZombieDropper(Greenfoot.getRandomNumber(360), d, d, z, this);
        getWorld().addObject(pack, getX(), getY());
    }
}
