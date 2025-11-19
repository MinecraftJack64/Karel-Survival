package com.karel.game.weapons.acidart;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

public class AcidDart extends Bullet {
    public AcidDart(double rot, GridObject source){
        super(rot, source);
        setSpeed(15);
        setLife(23);
        setDamage(200);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        targ.addObjectHere(new AcidLeak(this, targ));
    }
}
