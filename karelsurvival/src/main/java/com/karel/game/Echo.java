package com.karel.game;

/**
 * Used by Shards and CrystalGun
 * 
 * @author Poul Henriksen
 */
public class Echo extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    public Echo(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(10);
        setLife(14);
        setDamage(50);
        setNumTargets(-1);
    }
}
