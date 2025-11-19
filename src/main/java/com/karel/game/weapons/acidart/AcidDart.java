package com.karel.game.weapons.acidart;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

public class AcidDart extends Bullet {
    boolean isUpgrade, isGadget;
    public AcidDart(double rot, GridObject source, boolean upgrade, boolean gadget){
        super(rot, source);
        setSpeed(15);
        setLife(23);
        setDamage(200);
        isUpgrade = upgrade;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        AcidLeak t = new AcidLeak(this, targ, isUpgrade);
        targ.addObjectHere(t);
        if(isGadget){
            t.gadget();
        }
    }
}
