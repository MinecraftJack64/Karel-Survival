package com.karel.game.gridobjects.gridentities.zombies.laser;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZLaser extends ZBullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 200;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */

    public ZLaser(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/laser.png");
        scaleTexture(30);
        setLife(25);
    }
}
