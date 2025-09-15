package com.karel.game.effects;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * Deal damage over time like poison, but damages indefinitely until removed.
 * ID "fatal_poison"
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FatalPoisonEffect extends Effect
{
    private int damage, cooldown, interval;
    public FatalPoisonEffect(int damage, int interval, GridObject source){
        this(damage, interval, source, new EffectID(source));
    }
    public FatalPoisonEffect(int damage, int interval, GridObject source, EffectID id){
        super(id);
        setSource(source);
        this.damage = (int)(damage*getSource().getPower());
        setCollisionProtocol(3);
        cooldown = this.interval = interval;
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/fatal_poison.png";
    }
    public void affect(){
        cooldown--;
        if(cooldown<=0){
            damage(getTarget());
            cooldown = interval;
        }
    }
    public void damage(GridEntity e){
        e.hitIgnoreShield(damage, getSource());
    }
}
