package com.karel.game.weapons.catclaw;

import com.karel.game.Boomerang;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Claw extends Boomerang
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public Claw(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(25);
        setReturnSpeed(20);
        setLife(9);
        setReturnDamage(75);
        setDamageOnReturn(2);
        setNumTargets(-1);
    }
}
