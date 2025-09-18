package com.karel.game.gridobjects.gridentities.zombies.splitter;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.SpawnableZombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
/**
 * An extremely tanky and slow zombie spawned when a splitter zombie dies.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HeavyZombie extends SpawnableZombie
{
    private ZombieClass[] classes = new ZombieClass[]{ZombieClass.tank};
    public String getStaticTextureURL(){return "heavyzareln.png";}
    public HeavyZombie(GridObject parent)
    {
        this();
        inherit(parent);
    }
    public HeavyZombie(){
        setSpeed(0.2);
        startHealth(4000);
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 150;
    }
    public String getName(){
        return "Heavy Zombie";
    }
    @Override
    public String getZombieID(){
        return "heavy";
    }
}
