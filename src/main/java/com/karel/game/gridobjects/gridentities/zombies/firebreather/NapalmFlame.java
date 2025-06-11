package com.karel.game.gridobjects.gridentities.zombies.firebreather;

import com.karel.game.GridObject;

public class NapalmFlame extends Flame
{

    public NapalmFlame(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/napalm.png");
        setDamage(20);
    }
}
