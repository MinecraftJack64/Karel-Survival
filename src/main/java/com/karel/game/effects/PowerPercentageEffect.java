package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * Temporarily modifies the target's power by a percentage. Power usually determines how much damage or healing the target does.
 * A percentage of 1 means no change, less than 1 means a decrease, and greater than 1 means an increase.
 * ID "power"
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerPercentageEffect extends PercentageEffect
{
    public PowerPercentageEffect(double percentage, int duration, GridObject source){
        super(percentage, duration, source);
    }
    public PowerPercentageEffect(double percentage, int duration, GridObject source, EffectID id){
        super(percentage, duration, source, id);
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/power"+(isMalicious()?"Mal":"")+".png";
    }
    public void onApply(){
        getTarget().setPower(getPercentage(), getID());
    }
    public void onClear(){
        getTarget().setPower(1, getID());
    }
}
