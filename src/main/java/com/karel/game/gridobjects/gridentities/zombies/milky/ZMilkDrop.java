package com.karel.game.gridobjects.gridentities.zombies.milky;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZMilkDrop extends ZBullet
{
    public ZMilkDrop(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(15);
        setLife(35);
        setDamage(30);
        setImage("Projectiles/Bullets/melonseed.png");
        scaleTexture(20);
        setRotation(getRotation()-180);
    }
}
