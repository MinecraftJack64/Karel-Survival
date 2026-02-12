package com.karel.game.gridobjects.gridentities.zombies.beermartyr;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

public class ZBeerBreath extends ZBullet{
    public ZBeerBreath(double rotation, GridObject source){
        super(rotation, source);
        setSpeed(3);
        setLife(200);
        setNumTargets(-1);
        setDamage(50);
    }
    public void doHit(GridEntity t){
        t.applyEffect(new PoisonEffect(10, 30, 5, this));
        super.doHit(t);
    }
}
