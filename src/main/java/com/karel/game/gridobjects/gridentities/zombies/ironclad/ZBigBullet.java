package com.karel.game.gridobjects.gridentities.zombies.ironclad;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZBigBullet extends ZBullet
{
    
    public ZBigBullet(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(20);
        setLife(100);
        setDamage(500);
    }
}
