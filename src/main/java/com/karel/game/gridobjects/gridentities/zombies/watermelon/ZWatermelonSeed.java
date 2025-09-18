package com.karel.game.gridobjects.gridentities.zombies.watermelon;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZWatermelonSeed extends ZBullet
{
    public ZWatermelonSeed(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(17);
        setLife(15);
        setDamage(10);
        setImage("Projectiles/Bullets/melonseed.png");
        scaleTexture(20);
        setRotation(getRotation()-180);
    }
}
