package com.karel.game.weapons.scream;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.SilenceEffect;
import com.karel.game.weapons.EffectID;

public class DeafeningScreamWave extends ScreamWave {
    public DeafeningScreamWave(double rotation, int life, int damage, GridObject source)
    {
        super(rotation, life, damage, source);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(getLife()>5)targ.applyEffect(new SilenceEffect(getLife(), new EffectID("scream attack")){
            public int getCollisionProtocol(){
                return 2;
            }
        });
    }
}
