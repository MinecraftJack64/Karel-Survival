package com.karel.game.weapons.shotgun;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Shot extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public Shot(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/shotgun/proj.png");
        scaleTexture(25);
        setRotation(rotation+45);
        setSpeed(17);
        setLife(12);
        setDamage(50);
    }
}
