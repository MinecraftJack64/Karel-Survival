package com.karel.game.gridobjects.gridentities.zombies.marksman;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZSBullet extends ZBullet
{
    
    public ZSBullet(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(20);
        setLife(100);
        setDamage(300);
    }
}
