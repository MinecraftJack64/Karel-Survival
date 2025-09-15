package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * Temporarily modifies the target's reload speed by a percentage. Reload speed usually determines how quickly the target can use abilities that have a cooldown.
 * ID "reload"
 * @author (your name)
 * @version (a version number or a date)
 */
public class ReloadPercentageEffect extends PercentageEffect
{
    // instance variables - replace the example below with your own
    public ReloadPercentageEffect(double percentage, int duration, GridObject source){
        super(percentage, duration, source);
    }
    public ReloadPercentageEffect(double percentage, int duration, GridObject source, EffectID id){
        super(percentage, duration, source, id);
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/reload"+(isMalicious()?"Mal":"")+".png";
    }
    public void onApply(){
        getTarget().setReloadMultiplier(getPercentage(), getID());
    }
    public void onClear(){
        getTarget().setReloadMultiplier(1, getID());
    }
}
