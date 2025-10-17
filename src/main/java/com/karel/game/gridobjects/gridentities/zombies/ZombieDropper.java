package com.karel.game.gridobjects.gridentities.zombies;

import com.karel.game.Dropper;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZombieDropper extends Dropper
{
    public ZombieDropper(double rotation, double targetdistance, double height, GridObject toSpawn, GridObject source)
    {
        super(rotation, targetdistance, height, toSpawn, source);
        setImage("zdrop.png");
    }
}
