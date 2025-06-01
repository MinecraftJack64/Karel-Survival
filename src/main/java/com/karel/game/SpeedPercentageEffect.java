package com.karel.game;

import com.karel.game.weapons.EffectID;

/**
 * Write a description of class SpeedPercentageEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpeedPercentageEffect extends PercentageEffect
{
    // instance variables - replace the example below with your own
    public SpeedPercentageEffect(double percentage, int duration, GridObject source){
        super(percentage, duration, source);
    }
    public SpeedPercentageEffect(double percentage, int duration, GridObject source, EffectID id){
        super(percentage, duration, source, id);
    }
    public void onApply(){
        getTarget().setSpeedMultiplier(getPercentage(), getID());
    }
    public void onClear(){
        getTarget().setSpeedMultiplier(1, getID());
    }
}
