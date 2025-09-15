package com.karel.game;
import java.util.ArrayList;

import com.karel.game.effects.ImmobilizeEffect;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieDropper;
import com.karel.game.gridobjects.gridentities.zombies.ZombiePackage;
import com.karel.game.gridobjects.gridentities.zombies.exploding.ExplodingZombie;
import com.karel.game.gridobjects.gridentities.zombies.hardhat.HardHatZombie;
import com.karel.game.gridobjects.gridentities.zombies.shield.ShieldZombie;

/**
 * Write a description of class Turret here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BombTurret extends ZombiePackage
{
    private int type;
    private ArrayList<GridEntity> myspawns;
    public BombTurret(int type, ArrayList<GridEntity> toTrack, GridObject source){
        super(source);
        this.type = type;
        this.myspawns = toTrack;
    }
    //1 - just a shooter zombie
    //2 - zombies as meatshields
    //3 - shield zombies as meatshields
    //4 - upgrade to marksmen
    //5 - some are hardhats
    public void update(){
        //setLocation(getX(), getY());
        switch(type){
            case 1:
                spawnZombie(newExploding());
            break;
            case 2:
                spawnZombie(newExploding());
                spawnZombieAt(newZombie(), getXAtOffset(-1), getYAtOffset(0));
                spawnZombieAt(newZombie(), getXAtOffset(0), getYAtOffset(1));
                spawnZombieAt(newZombie(), getXAtOffset(1), getYAtOffset(0));
                spawnZombieAt(newZombie(), getXAtOffset(0), getYAtOffset(-1));
            break;
            case 3:
                spawnZombie(newExploding());
                spawnZombieAt(newShield(), getXAtOffset(-1), getYAtOffset(0));
                spawnZombieAt(newShield(), getXAtOffset(0), getYAtOffset(1));
                spawnZombieAt(newShield(), getXAtOffset(1), getYAtOffset(0));
                spawnZombieAt(newShield(), getXAtOffset(0), getYAtOffset(-1));
            break;
            case 4:
                spawnZombie(newExploding());
                spawnZombieAt(newShield(), getXAtOffset(-1), getYAtOffset(0));
                spawnZombieAt(newShield(), getXAtOffset(0), getYAtOffset(1));
                spawnZombieAt(newShield(), getXAtOffset(1), getYAtOffset(0));
                spawnZombieAt(newShield(), getXAtOffset(0), getYAtOffset(-1));
            break;
            case 5:
                spawnZombie(newExploding());
                spawnZombieAt(newHardHat(), getXAtOffset(-1), getYAtOffset(0));
                spawnZombieAt(newShield(), getXAtOffset(0), getYAtOffset(1));
                spawnZombieAt(newHardHat(), getXAtOffset(1), getYAtOffset(0));
                spawnZombieAt(newShield(), getXAtOffset(0), getYAtOffset(-1));
            break;
        }
        getWorld().removeObject(this);
    }
    public GridObject newExploding(){
        GridEntity z = new ExplodingZombie();
        myspawns.add(z);
        return z;
    }
    public GridObject newZombie(){
        GridEntity z = new Zombie();
        myspawns.add(z);
        return z;
    }
    public GridObject newShield(){
        GridEntity z = new ShieldZombie();
        myspawns.add(z);
        return z;
    }
    public GridObject newHardHat(){
        GridEntity z = new HardHatZombie();
        z.applyEffect(new ImmobilizeEffect(800, getSource()));
        myspawns.add(z);
        return z;
    }
    public void spawnZombie(GridObject z){
        getWorld().addObject(z, getX(), getY());
    }
    public void spawnZombieAt(GridObject z, double x, double y){
        double d = distanceTo(x, y);
        ZombieDropper pack = new ZombieDropper(getAngle(x, y)+90, d, d, z, this);
        getWorld().addObject(pack, getX(), getY());
    }
}
