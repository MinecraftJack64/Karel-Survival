package com.karel.game;
/**
 * Temporarily makes the target undetectable to others TODO
 * @author MinecraftJack64
 */
public class InvisibilityEffect extends DurationEffect
{
    public InvisibilityEffect(int duration, GridObject source){
        super(duration, source);
    }
    public InvisibilityEffect(int duration, GridObject source, EffectID id){
        super(duration, source, id);
    }
    public InvisibilityEffect(int duration, EffectID id){
        super(duration, id);
    }
    public void onClear(){
        getTarget().setDetectable(true);// TODO: accountability
        getTarget().setOpacityPercent(1);
    }
    public void onApply(){
        getTarget().setDetectable(false);
        getTarget().setOpacityPercent(0.5);
    }
}
