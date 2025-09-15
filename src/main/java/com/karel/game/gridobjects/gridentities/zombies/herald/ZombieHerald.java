package com.karel.game.gridobjects.gridentities.zombies.herald;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Does not move, indicates that killing it will result in the boss spawning. Takes on the appearance of the boss
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ZombieHerald extends Zombie
{
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.meatshield};
    public String getStaticTextureURL(){
        return "heraldzareln.png";
    }
    public ZombieHerald()
    {
        setSpeed(0);
        startHealth(200);
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 0;
    }
    public String getName(){
        return "Zombie Herald";
    }
}
