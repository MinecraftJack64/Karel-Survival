package com.karel.game.gridobjects.gridentities.zombies.ribbondancer;

import com.karel.game.Boomerang;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZRibbon extends Boomerang
{
    /** The damage this bullet will deal */
    //private static final int damage = 200;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    
    public ZRibbon(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/zbullet.png");
        setLife(40);
        setSpeed(13);
        setDamage(150);
    }
}
