package com.karel.game.gridobjects.gridentities.zombies.stunt;
import com.karel.game.gridobjects.hitters.StickyBullet;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZStickyBomb extends StickyBullet
{
    
    public ZStickyBomb(double rotation, GridObject source)
    {
        super(rotation, 90, source);
        setSpeed(18);
        setLife(35);
        setDamage(300);
    }
}
