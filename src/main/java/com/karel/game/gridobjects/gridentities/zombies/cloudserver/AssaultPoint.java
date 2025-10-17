package com.karel.game.gridobjects.gridentities.zombies.cloudserver;
import java.util.ArrayList;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZombiePackage;
import com.karel.game.gridobjects.gridentities.zombies.breadboxer.BreadBoxerZombie;
import com.karel.game.gridobjects.gridentities.zombies.cop.CopZombie;
import com.karel.game.gridobjects.gridentities.zombies.watermelon.WatermelonZombie;

/**
 * Write a description of class Turret here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AssaultPoint extends ZombiePackage
{
    private int spawnCount, spawnCooldown, seed;
    private ArrayList<GridEntity> mySpawns;
    public AssaultPoint(ArrayList<GridEntity> toTrack, GridObject source){
        super(source);
        setImage("zdrop.png");
        scaleTexture(40);
        this.mySpawns = toTrack;
        seed = Greenfoot.getRandomNumber(3);
        spawnCount = Greenfoot.getRandomNumber(2)+3;
    }
    public void update(){
        if(spawnCooldown<=0){
            spawnCooldown = Greenfoot.getRandomNumber(150)+450;
            spawnCount--;
            int choice = spawnCount+seed;
            choice%=3;
            switch(choice){
                case 0:
                    spawnZombie(new BreadBoxerZombie());
                    break;
                case 1:
                    spawnZombie(new WatermelonZombie());
                    break;
                case 2:
                    spawnZombie(new CopZombie());
                    break;
            }
            if(spawnCount<=0){
                getWorld().removeObject(this);
            }
        }else{
            spawnCooldown--;
        }
    }
    public void spawnZombie(GridEntity z){
        mySpawns.add(z);
        getWorld().addObject(z, getX(), getY());
    }
}
