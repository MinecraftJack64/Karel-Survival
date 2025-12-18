package com.karel.game.gridobjects.gridentities.zombies.urchin;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZUrchinSpine extends ZBullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 200;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    
    public ZUrchinSpine(double rotation, GridObject source)
    {
        super(rotation, source);
        setLife(100);
        setSpeed(6);
        setDamage(150);
        setNumTargets(2);
    }
}
