package com.karel.game.gridobjects.gridentities.zombies.stunt;

import com.karel.game.gridobjects.gridentities.zombies.ZBullet;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZStickyBomb extends ZBullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 300;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 150;
    
    public ZStickyBomb(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(18);
        setLife(35);
        setDamage(300);
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
}
