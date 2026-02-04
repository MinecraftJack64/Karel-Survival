package com.karel.game.gridobjects.gridentities.zombies.minishooter;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZMiniBullet extends ZBullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 200;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    
    public ZMiniBullet(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/zbullet.png");
        setLife(23);
        setSpeed(10);
        setDamage(50);
    }
}
