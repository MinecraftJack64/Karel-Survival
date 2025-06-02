package com.karel.game;

import com.karel.game.weapons.EffectID;

/**
 * Write a description of class SpeedPercentageEffect here.
 * 
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
    public void onApply(){
        getTarget().setReloadMultiplier(getPercentage(), getID());
    }
    public void onClear(){
        getTarget().setReloadMultiplier(1, getID());
    }
}
