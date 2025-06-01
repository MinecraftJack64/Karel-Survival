package com.karel.game.weapons.scream;

import com.karel.game.Bullet;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ScreamWave extends Bullet
{
    /** The damage this bullet will deal */
    private static final double range = 300;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    public ScreamWave(double rotation, int life, int damage, GridObject source)
    {
        super(rotation, source);
        setSpeed(range/life);
        setLife(life);
        setDamage(damage);
        setNumTargets(-1);
    }
}
