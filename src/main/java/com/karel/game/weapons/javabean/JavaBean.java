package com.karel.game.weapons.javabean;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class JavaBean extends Bullet
{
    /** A bullet looses one life each act, and will disappear when life = 0 */
    public JavaBean(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("brick.png");
        setRotation(rotation);
        scaleTexture(20);
        setSpeed(25);
        setLife(20);
        setDamage(100);
        setNumTargets(-1);
    }
}
