package com.karel.game;
import java.util.ArrayList;

/**
 * Write a description of class LaserSquad here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LaserSquad extends ZombiePackage
{
    private int type;
    private ArrayList<GridEntity> myspawns;
    public LaserSquad(int type, ArrayList<GridEntity> toTrack, GridObject source){
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
        //setRealLocation(getRealX(), getRealY());
        switch(type){
            case 1:
                spawnZombie(newLaser());
            break;
            case 2:
                spawnZombieAt(newLaser(), getXAtOffset(-1), getYAtOffset(0));
                spawnZombieAt(newLaser(), getXAtOffset(1), getYAtOffset(0));
            break;
            case 3:
                spawnZombieAt(newLaser(), getXAtOffset(-1), getYAtOffset(1));
                spawnZombieAt(newLaser(), getXAtOffset(1), getYAtOffset(1));
                spawnZombieAt(newLaser(), getXAtOffset(0), getYAtOffset(-1));
            break;
            case 4:
                spawnZombieAt(newLaser(), getXAtOffset(-1), getYAtOffset(0));
                spawnZombieAt(newLaser(), getXAtOffset(0), getYAtOffset(1));
                spawnZombieAt(newLaser(), getXAtOffset(1), getYAtOffset(0));
                spawnZombieAt(newLaser(), getXAtOffset(0), getYAtOffset(-1));
            break;
            case 5:
                spawnZombie(newLaser());
                spawnZombieAt(newLaser(), getXAtOffset(-1), getYAtOffset(0));
                spawnZombieAt(newLaser(), getXAtOffset(0), getYAtOffset(1));
                spawnZombieAt(newLaser(), getXAtOffset(1), getYAtOffset(0));
                spawnZombieAt(newLaser(), getXAtOffset(0), getYAtOffset(-1));
            break;
        }
        die();
    }
    public void die(){
        super.die();
        getWorld().removeObject(this);
    }
    public GridObject newLaser(){
        GridEntity z = new LaserZombie();
        myspawns.add(z);
        return z;
    }
    public void spawnZombie(GridObject z){
        getWorld().addObject(z, getRealX(), getRealY());
    }
    public void spawnZombieAt(GridObject z, double x, double y){
        double d = distanceTo(x, y);
        ZombieDropper pack = new ZombieDropper(getAngle(x, y)+90, d, d, z, this);
        getWorld().addObject(pack, getRealX(), getRealY());
    }
}
