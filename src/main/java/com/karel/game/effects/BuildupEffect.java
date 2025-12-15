package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * Temporarily stuns the target, stopping their movement and abilities.
 * ID "stun"
 * @author MinecraftJack64
 */
public class BuildupEffect extends DurationEffect
{
    public BuildupEffect(int duration, GridObject source){
        super(duration, source);
    }
    public BuildupEffect(int duration, GridObject source, EffectID id){
        super(duration, source, id);
    }
    public BuildupEffect(int duration, EffectID id){
        super(duration, id);
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/stun.png";
    }
    public void onClear(){
        getTarget().unstun(getID());
    }
    public void onApply(){
        getTarget().stun(getID());
    }
    public String getStringID(){return "stun";}
}
