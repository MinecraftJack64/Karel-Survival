package com.karel.game.weapons.scalpel;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.LifestealEffect;
import com.karel.game.effects.StunEffect;
import com.karel.game.gridobjects.hitters.Bullet;

public class Anesthetizer extends Bullet{
    public Anesthetizer(double rot, GridObject source){
        super(rot, source);
        setDamage(100);
        setNumTargets(5);
        setSpeed(15);
        setLife(40);
    }
    public void doHit(GridEntity t){
        LifestealEffect effect = new LifestealEffect(5, 5, 60, (GridEntity)getSource());
        effect.setRatio(10);
        t.applyEffect(effect);
        t.applyEffect(new StunEffect(90, this));
        super.doHit(t);
    }
}
