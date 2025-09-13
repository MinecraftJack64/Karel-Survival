package com.karel.game.gridobjects.gridentities.zombies.warrior;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZSpear extends ZBullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 300;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 150;
    
    public ZSpear(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(18);
        setLife(35);
        setDamage(300);
        setImage("Projectiles/Bullets/spear.png");
        scaleTexture(60);
        setRotation(getRotation()-180);
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
}
