package com.karel.game.weapons.inferno;

import com.karel.game.weapons.EffectID;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.BurnEffect;
import com.karel.game.HealCharge;

/**
 * Deal damage over time
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SoulBurnEffect extends BurnEffect
{
    public SoulBurnEffect(int damage, int interval, int times, GridObject source){
        this(damage, interval, times, source, new EffectID(source));
    }
    public SoulBurnEffect(int damage, int interval, int times, GridObject source, EffectID id){
        super(damage, interval, times, source, id);
    }
    public void damage(GridEntity e){
        super.damage(e);
        if(e.isDead() && e.distanceTo(getSource())<=100){
            e.addObjectHere(new HealCharge(0, getSource().getParentAffecter(), 20));
        }
    }
}
