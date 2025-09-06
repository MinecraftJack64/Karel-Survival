package com.karel.game.effects;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * Deal damage(poison) over time at a preset interval for a preset number of times. Hits through shields.
 * ID "poison"
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
