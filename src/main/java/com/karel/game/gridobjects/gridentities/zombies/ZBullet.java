package com.karel.game.gridobjects.gridentities.zombies;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZBullet extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 200;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    
    public ZBullet(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/zbullet.png");
        setLife(40);
        setSpeed(13);
        setDamage(150);
    }
}
