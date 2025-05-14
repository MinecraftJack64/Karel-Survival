package com.karel.game;
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class CampfireDropper extends Dropper
{
    public CampfireDropper(double rotation, double targetdistance, double height, GridObject toSpawn, GridObject source)
    {
        super(rotation, targetdistance, height, toSpawn, source);
    }
}
