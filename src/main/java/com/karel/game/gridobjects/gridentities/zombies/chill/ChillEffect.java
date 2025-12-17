package com.karel.game.gridobjects.gridentities.zombies.chill;

import com.karel.game.GridObject;
import com.karel.game.PercentageShield;
import com.karel.game.effects.BuildupEffect;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.StunEffect;
import com.karel.game.shields.ShieldID;

public class ChillEffect extends BuildupEffect{
    public static int maxDuration = 1200;
    public ChillEffect(int duration, GridObject source){
        super(duration, maxDuration, source);
    }
    public ChillEffect(int duration, int maxDuration, GridObject source, EffectID id){
        super(duration, maxDuration, source, id);
    }
    public void score(){
        getTarget().applyEffect(new StunEffect(100, getSource()));
        getTarget().applyShield(new PercentageShield(new ShieldID(getSource()), 0.5, 100));
        getSource().damage(getTarget(), 100);
    }
}
