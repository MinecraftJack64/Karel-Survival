package com.karel.game.gridobjects.gridentities.zombies.splitter;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * A normal zombie that spawns a light zombie and heavy zombie on death.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SplitterZombie extends Zombie
{
    private ZombieClass[] classes = new ZombieClass[]{ZombieClass.spawner};
    public String getStaticTextureURL(){return "splitzareln.png";}
    public SplitterZombie()
    {
        super();
    }
    public void die(GridObject cause){
        LightZombie l = new LightZombie(this);
        HeavyZombie h = new HeavyZombie(this);
        addObjectHere(l);
        addObjectHere(h);
        super.die(cause);
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 0;
    }
    public String getName(){
        return "Splitter Zombie";
    }
    @Override
    public String getZombieID(){
        return "splitter";
    }
}
