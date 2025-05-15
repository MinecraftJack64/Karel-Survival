package com.karel.game;
import java.util.ArrayList;

/**
 * Write a description of class Turret here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Turret extends ZombiePackage
{
    private int type;
    private ArrayList<GridEntity> myspawns;
    public Turret(int type, ArrayList<GridEntity> toTrack, GridObject source){
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
                spawnZombie(newShooter());
            break;
            case 2:
                spawnZombie(newShooter());
                spawnZombieAt(newZombie(), getXAtOffset(-1), getYAtOffset(0));
                spawnZombieAt(newZombie(), getXAtOffset(0), getYAtOffset(1));
                spawnZombieAt(newZombie(), getXAtOffset(1), getYAtOffset(0));
                spawnZombieAt(newZombie(), getXAtOffset(0), getYAtOffset(-1));
            break;
            case 3:
                spawnZombie(newShooter());
                spawnZombieAt(newShield(), getXAtOffset(-1), getYAtOffset(0));
                spawnZombieAt(newShield(), getXAtOffset(0), getYAtOffset(1));
                spawnZombieAt(newShield(), getXAtOffset(1), getYAtOffset(0));
                spawnZombieAt(newShield(), getXAtOffset(0), getYAtOffset(-1));
            break;
            case 4:
                spawnZombie(newMarksman());
                spawnZombieAt(newShield(), getXAtOffset(-1), getYAtOffset(0));
                spawnZombieAt(newShield(), getXAtOffset(0), getYAtOffset(1));
                spawnZombieAt(newShield(), getXAtOffset(1), getYAtOffset(0));
                spawnZombieAt(newShield(), getXAtOffset(0), getYAtOffset(-1));
            break;
            case 5:
                spawnZombie(newMarksman());
                spawnZombieAt(newHardHat(), getXAtOffset(-1), getYAtOffset(0));
                spawnZombieAt(newShield(), getXAtOffset(0), getYAtOffset(1));
                spawnZombieAt(newHardHat(), getXAtOffset(1), getYAtOffset(0));
                spawnZombieAt(newShield(), getXAtOffset(0), getYAtOffset(-1));
            break;
        }
        getWorld().removeObject(this);
    }
    public GridObject newShooter(){
        GridEntity z = new ShooterZombie();
        z.applyEffect(new ImmobilizeEffect(800, getSource()));
        myspawns.add(z);
        return z;
    }
    public GridObject newMarksman(){
        GridEntity z = new MarksmanZombie();
        z.applyEffect(new ImmobilizeEffect(800, getSource()));
        myspawns.add(z);
        return z;
    }
    public GridObject newZombie(){
        GridEntity z = new Zombie();
        z.applyEffect(new ImmobilizeEffect(800, getSource()));
        myspawns.add(z);
        return z;
    }
    public GridObject newShield(){
        GridEntity z = new ShieldZombie();
        z.applyEffect(new ImmobilizeEffect(800, getSource()));
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
        getWorld().addObject(z, getRealX(), getRealY());
    }
    public void spawnZombieAt(GridObject z, double x, double y){
        double d = distanceTo(x, y);
        ZombieDropper pack = new ZombieDropper(getAngle(x, y)+90, d, d, z, this);
        getWorld().addObject(pack, getRealX(), getRealY());
    }
}
