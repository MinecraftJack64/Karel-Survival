package com.karel.game.gridobjects.gridentities.zombies.chill;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.HomingFlyingProjectile;

public class IceBolt extends HomingFlyingProjectile {
    public IceBolt(double rot, double dist, double h, GridObject source, GridEntity targ){
        super(rot, dist, h, source, targ);
        setDamage(0);
    }
    public void doHit(GridEntity targ){
        addObjectHere(new IceBlock(targ, getSource()));
    }
}
