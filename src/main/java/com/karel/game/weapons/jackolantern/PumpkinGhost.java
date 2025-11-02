package com.karel.game.weapons.jackolantern;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.HealthCurseEffect;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.hitters.Bullet;

public class PumpkinGhost extends Bullet{
    public PumpkinGhost(double r, GridObject source){
        super(r, source);
        setDamage(0);
        setLife(55);
        setSpeed(9);
        setNumTargets(-1);
    }
    public void doHit(GridEntity targ){
        targ.applyEffect(new HealthCurseEffect(700, 100, this));
        super.doHit(targ);
    }
    public void onExitWall(){ //TODO
        explodeOnEnemies(100, (e)->{
            e.applyEffect(new SpeedPercentageEffect(0.5, 60, this));
        });
        knockBackOnEnemies(100, 50);
    }
}
