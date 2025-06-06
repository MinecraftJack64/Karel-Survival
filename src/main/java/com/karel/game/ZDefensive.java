package com.karel.game;

import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZDefensive extends ZBullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 300;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 150;
    
    public ZDefensive(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(16);
        setLife(28);
        setDamage(100);
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
}
