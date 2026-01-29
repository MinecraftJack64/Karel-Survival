package com.karel.game.gridobjects.gridentities.zombies.ribbondancer;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZStabBullet extends ZBullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 200;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    
    public ZStabBullet(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/zbullet.png");
        setLife(7);
        setNumTargets(-1);
        setSpeed(20);
        setDamage(150);
    }
}
