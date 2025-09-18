package com.karel.game;

import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class GrenadeFragment extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public GrenadeFragment(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(20);
        setLife(6);
        setDamage(20);
    }
}
