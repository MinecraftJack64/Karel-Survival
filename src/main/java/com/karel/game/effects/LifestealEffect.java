package com.karel.game.effects;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * Deal damage over time to target while healing the source
 * ID "lifesteal"
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LifestealEffect extends TickingEffect
{
    private int damage;
    private GridEntity healtarget;
    /*
     * Use this if entity is applying effect directly
     */
    public LifestealEffect(int damage, int interval, int times, GridEntity source){
        this(damage, interval, times, source, new EffectID(source));
    }
    /*
     * Use this if an object like a projectile is applying effect on behalf of an entity
     */
    public LifestealEffect(int damage, int interval, int times, GridObject source, GridEntity target){
        this(damage, interval, times, source, target, new EffectID(source));
    }
    public LifestealEffect(int damage, int interval, int times, GridEntity source, EffectID id){
        this(damage, interval, times, source, source, id);
    }
    public LifestealEffect(int damage, int interval, int times, GridObject source, GridEntity target, EffectID id){
        super(interval, times, source, id);
        this.damage = (int)(damage*getSource().getPower());
        healtarget = target;
    }
    public boolean isMalicious(){
        return true;
    }
    public void tick(){
        damage(getTarget());
    }
    public void damage(GridEntity e){
        e.hit(damage, getSource());
        if(!healtarget.isDead()){
            healtarget.heal(healtarget, damage);
        }
    }
}
