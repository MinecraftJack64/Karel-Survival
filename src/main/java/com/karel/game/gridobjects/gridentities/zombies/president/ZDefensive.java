package com.karel.game.gridobjects.gridentities.zombies.president;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZDefensive extends ZBullet
{
    public ZDefensive(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(16);
        setLife(28);
        setDamage(100);
    }
}
