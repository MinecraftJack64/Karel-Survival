package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * Temporarily modifies the target's speed by a percentage. For example, a percentage of 0.5 halves the target's speed, while a percentage of 2 doubles it.
 * ID "speed"
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WeightPercentageEffect extends PercentageEffect
{
    // instance variables - replace the example below with your own
    public WeightPercentageEffect(double percentage, int duration, GridObject source){
        super(percentage, duration, source);
    }
    public WeightPercentageEffect(double percentage, int duration, GridObject source, EffectID id){
        super(percentage, duration, source, id);
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/speed"+(isMalicious()?"Mal":"")+".png";
    }
    public void onApply(){
        getTarget().setWeightMultiplier(getPercentage(), getID());
    }
    public void onClear(){
        getTarget().setWeightMultiplier(1, getID());
    }
    public String getStringID(){return "weight"+(isMalicious()?"Mal":"");}
}
