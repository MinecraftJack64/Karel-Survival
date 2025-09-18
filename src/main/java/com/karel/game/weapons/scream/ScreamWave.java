package com.karel.game.weapons.scream;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ScreamWave extends Bullet
{
    /** The damage this bullet will deal */
    private static final double range = 300;
    private int size = 1;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    public ScreamWave(double rotation, int life, int damage, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/scream/proj.png");
        setRotation(rotation);
        scaleTexture(1);
        setSpeed(range/life);
        setLife(life);
        setDamage(damage);
        setNumTargets(-1);
    }
    public void applyPhysics()
    {
        size+=getSpeed();
        scaleTexture(size);
        super.applyPhysics();
    }
}
