package com.karel.game.gridobjects.gridentities.zombies.ubiquitous;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Does not move, indicates that killing it will result in the boss spawning. Takes on the appearance of the boss
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UbiquitousZombie extends Zombie
{
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.whittler};
    public String getStaticTextureURL(){
        return "heraldzareln.png";
    }
    public UbiquitousZombie()
    {
        setSpeed(5);
        startHealth(200);
    }
    public void attack(){
        explodeOn(1250, 1, new UbiquitousExplosion(1250));
    }
    public int defaultReloadTime(){
        return 60;
    }
    public int defaultRange(){
        return 1200;
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 400;
    }
    public String getName(){
        return "Zombie Herald";
    }
    @Override
    public String getZombieID(){
        return "ubiquitous";
    }
}
