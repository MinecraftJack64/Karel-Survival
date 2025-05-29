package com.karel.game.weapons.spear;

import com.karel.game.Bullet;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SpearlessPunch extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    public SpearlessPunch(double rotation, GridObject source)
    {
        super(rotation, source);
        setRealRotation(getRealRotation() - 90);
        setImage("Weapons/spear/punch.png");
        setSpeed(20);
        setLife(6);
        setDamage(50);
        setNumTargets(-1);
    }
}
