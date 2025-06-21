package com.karel.game.gridobjects.gridentities.zombies.steak;

import com.karel.game.Dropper;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SteakDropper extends Dropper
{
    public SteakDropper(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, new Steak(source), source);
        setImage("Projectiles/Throws/steak.png");
        scaleTexture(50);
    }
}
