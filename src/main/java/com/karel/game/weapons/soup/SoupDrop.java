package com.karel.game.weapons.soup;
import java.util.List;

import com.karel.game.Dropper;
import com.karel.game.GridObject;

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
