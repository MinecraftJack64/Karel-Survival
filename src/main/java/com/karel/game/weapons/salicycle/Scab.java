package com.karel.game.weapons.salicycle;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.gridobjects.hitters.FlyingProjectile;

public class Scab extends FlyingProjectile {
    public Scab(double rotation, double targetdistance, GridObject source)
    {
        //focus is 0 to 1
        super(rotation, targetdistance, 30, source);
        setRange(25);
        setNumTargets(1);
        setDamage(30);
    }
    public void doHit(GridEntity t){
        t.applyEffect(new PoisonEffect(10, 20, 5, this));
        super.doHit(t);
    }
}
