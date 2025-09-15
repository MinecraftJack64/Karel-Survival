package com.karel.game.gridobjects.gridentities.zombies.splitter;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.SpawnableZombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
/**
 * An extremely squishy and fast zombie. Spawned when a splitter zombie dies. Starts attacking after short cooldown.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LightZombie extends SpawnableZombie
{
    private ZombieClass[] classes = new ZombieClass[]{ZombieClass.assault, ZombieClass.meatshield};
    public String getStaticTextureURL(){return "lightzareln.png";}
    private int waittime = 200;
    public LightZombie(GridObject parent)
    {
        this();
        inherit(parent);
    }
    public LightZombie(){
        setSpeed(10);
        startHealth(100);
    }
    public void attack(){
        waittime--;
        if(waittime<=0){
            super.attack();
        }
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 150;
    }
    public String getName(){
        return "Light Zombie";
    }
}
