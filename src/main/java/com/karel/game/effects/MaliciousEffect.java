package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * The superclass of all completely negative effects.
 * ID "malicious"
 * @author MinecraftJack64
 */
public class MaliciousEffect extends DurationEffect
{
    public MaliciousEffect(int duration, GridObject source){
        super(duration, source);
    }
    public MaliciousEffect(int duration, GridObject source, EffectID id){
        super(duration, source, id);
    }
    public MaliciousEffect(int duration, EffectID id){
        super(duration, id);
    }
    public boolean isMalicious(){
        return true;
    }
}
