package com.karel.game.weapons.grenade;

import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ExtraGrenadeFragment extends GrenadeFragment
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public ExtraGrenadeFragment(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(23);
        setLife(6);
        setDamage(5);
    }
}
