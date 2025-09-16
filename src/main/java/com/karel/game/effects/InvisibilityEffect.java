package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * Temporarily makes the target undetectable to others unless they are extremely close. Other members of the same team can still see the target.
 * ID "invisibility"
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
    public String getStaticTextureURL(){
        return "Symbols/Effects/invisibility.png";
    }
    public void onClear(){
        getTarget().setDetectable(true);// TODO: accountability
        getTarget().setOpacityPercent(1);
    }
    public void onApply(){
        getTarget().setDetectable(false);
        getTarget().setOpacityPercent(0.5);
    }
    public String getStringID(){return "invisibility";}
}
