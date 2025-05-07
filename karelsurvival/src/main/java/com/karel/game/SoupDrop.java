package com.karel.game;
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SoupDrop extends Dropper
{
    public SoupDrop(double rotation, double targetdistance, double height, double fc, GridObject source)
    {
        super(rotation, targetdistance, height, null, source);
        setLoad(new SoupBoost(fc, this));
    }
}
