package com.karel.game.gridobjects.gridentities.zombies.cloudserver;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.FlyingProjectile;

public class ZHailBall extends FlyingProjectile{
    public ZHailBall(double rot, double dist, double h, GridObject source){
        super(rot, dist, h, source);
        setDamage(0);
    }
}
