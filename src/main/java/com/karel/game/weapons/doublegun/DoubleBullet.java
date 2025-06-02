package com.karel.game.weapons.doublegun;

import com.karel.game.Bullet;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class DoubleBullet extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;

    public DoubleBullet(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(15);
        setLife(40);
        setDamage(40);
    }
}
