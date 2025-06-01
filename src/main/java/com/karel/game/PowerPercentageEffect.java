package com.karel.game;

import com.karel.game.weapons.EffectID;

/**
 * Write a description of class DamagePercentageEffect here.
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
    public void onApply(){
        getTarget().setPower(getPercentage(), getID());
    }
    public void onClear(){
        getTarget().setPower(1, getID());
    }
}
