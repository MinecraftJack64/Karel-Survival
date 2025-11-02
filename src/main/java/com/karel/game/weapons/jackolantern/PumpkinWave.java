package com.karel.game.weapons.jackolantern;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.SoftPullEffect;
import com.karel.game.gridobjects.hitters.Bullet;

public class PumpkinWave extends Bullet {
    public PumpkinWave(double r, GridObject source){
        super(r, source);
        setDamage(150);
        setSpeed(13);
        setLife(40);
        setNumTargets(-1);
        setMultiHit(false);
    }
    public void doHit(GridEntity targ){
        targ.applyEffect(new SoftPullEffect(getDirection(), 3, 10, this));
        super.doHit(targ);
    }
}
