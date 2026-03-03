package com.karel.game.weapons.droid;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

public class DroidShot extends Bullet
{
    
    public DroidShot(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/shotgun/proj.png");
        scaleTexture(25);
        setRotation(rotation+45);
        setSpeed(17);
        setLife(12);
        setDamage(7);
    }
}
