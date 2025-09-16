package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * Temporarily makes the target take more or less damage than usual.
 * For example, a percentage of 1.5 means the target takes 50% more damage, while
 * a percentage of 0.5 means the target takes 50% less damage.
 * ID "exposure"
 * @author MinecraftJack64
 */
public class DamageExposureEffect extends PercentageEffect
{
    // percentage is the amount of damage to be taken by target
    public DamageExposureEffect(double percentage, int duration, GridObject source){
        super(percentage, duration, source);
    }
    public DamageExposureEffect(double percentage, int duration, GridObject source, EffectID id){
        super(percentage, duration, source, id);
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/exposure"+(isMalicious()?"Mal":"")+".png";
    }
    public void onApply(){
        getTarget().setExposure(getPercentage(), getID());
    }
    public void onClear(){
        getTarget().setExposure(1, getID());
    }
    public boolean isMalicious(){
        return getPercentage()>1;
    }
    public String getStringID(){return "exposure"+(isMalicious()?"Mal":"");}
}
