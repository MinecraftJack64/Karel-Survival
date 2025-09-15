package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * Temporarily modifies the target's size by a percentage. A percentage of 1 means no change, less than 1 means a decrease, and greater than 1 means an increase.
 * A larger size increases hitbox size and damage and reduces damage taken. A smaller size does the opposite.
 * ID "size"
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SizePercentageEffect extends PercentageEffect
{
    public SizePercentageEffect(double percentage, int duration, GridObject source){
        super(percentage, duration, source);
    }
    public SizePercentageEffect(double percentage, int duration, GridObject source, EffectID id){
        super(percentage, duration, source, id);
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/size"+(isMalicious()?"Mal":"")+".png";
    }
    public void onApply(){
        getTarget().scaleSize(getPercentage(), getID());
    }
    public void onClear(){
        getTarget().scaleSize(1.0/getPercentage(), getID());
    }
}
