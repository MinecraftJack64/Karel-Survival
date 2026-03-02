package com.karel.game.weapons.droid;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

public class DroidLaser extends Bullet{
    public DroidLaser(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/laser.png");
        scaleTexture(50);
        setLife(25);
        setSpeed(20);
        setDamage(300);
    }
}
