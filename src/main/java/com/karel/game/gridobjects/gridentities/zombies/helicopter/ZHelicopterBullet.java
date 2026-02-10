package com.karel.game.gridobjects.gridentities.zombies.helicopter;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.AerialBullet;

public class ZHelicopterBullet extends AerialBullet{
    public ZHelicopterBullet(double rotation, double height, double distance, GridObject source)
    {
        super(rotation, height, distance/(height/10), 10, source);
        setDamage(300);
    }
}
