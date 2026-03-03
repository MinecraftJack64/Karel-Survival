package com.karel.game.weapons.droid;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

public class DrillBit extends Bullet
{
    public DrillBit(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/captorch/proj.png");
        scaleTexture(50);
        setRotation(getRotation()-180);
        setSpeed(18);
        setLife(7);
        setDamage(15);
        setNumTargets(-1);
    }
}
