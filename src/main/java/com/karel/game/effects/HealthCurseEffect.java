package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * Temporarily reduces the max health of the target.
 * ID "immobilize"
 * @author MinecraftJack64
 */
public class HealthCurseEffect extends MaliciousEffect
{
    int amt;
    public HealthCurseEffect(int duration, int amount, GridObject source){
        super(duration, source);
        amt = amount;
    }
    public HealthCurseEffect(int duration, int amount, GridObject source, EffectID id){
        super(duration, source, id);
        amt = amount;
    }
    public HealthCurseEffect(int duration, int amount, EffectID id){
        super(duration, id);
        amt = amount;
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/immobilize.png";
    }
    public void onApply(){
        getTarget().setMaxHealthLimit(amt, getID());
    }
    public void onClear(){
        getTarget().clearMaxHealthLimit(getID());
    }
    public String getStringID(){return "health_curse";}
}
