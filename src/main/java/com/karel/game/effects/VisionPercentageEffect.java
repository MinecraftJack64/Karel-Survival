package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * Temporarily modifies the target's reload speed by a percentage. Reload speed usually determines how quickly the target can use abilities that have a cooldown.
 * ID "reload"
 * @author (your name)
 * @version (a version number or a date)
 */
public class VisionPercentageEffect extends PercentageEffect
{
    // instance variables - replace the example below with your own
    public VisionPercentageEffect(double percentage, int duration, GridObject source){
        super(percentage, duration, source);
    }
    public VisionPercentageEffect(double percentage, int duration, GridObject source, EffectID id){
        super(percentage, duration, source, id);
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/vision"+(isMalicious()?"Mal":"")+".png";
    }
    public void onApply(){
        getTarget().setVisionMultiplier(getPercentage(), getID());
    }
    public void onClear(){
        getTarget().setVisionMultiplier(1, getID());
    }
    public String getStringID(){return "vision"+(isMalicious()?"Mal":"");}
}
