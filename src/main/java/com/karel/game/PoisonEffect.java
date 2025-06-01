package com.karel.game;

import com.karel.game.weapons.EffectID;

/**
 * Deal damage over time
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PoisonEffect extends TickingEffect
{
    private int damage;
    public PoisonEffect(int damage, int interval, int times, GridObject source){
        this(damage, interval, times, source, new EffectID(source));
    }
    public PoisonEffect(int damage, int interval, int times, GridObject source, EffectID id){
        super(interval, times, source, id);
        this.damage = (int)(damage*getSource().getPower());
    }
    public boolean isMalicious(){
        return true;
    }
    public void tick(){
        damage(getTarget());
    }
    public void damage(GridEntity e){
        e.hitIgnoreShield(damage, getSource());
    }
}
