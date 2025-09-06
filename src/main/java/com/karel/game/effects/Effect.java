package com.karel.game.effects;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/* all compound/mask effects
 * poison - ticking(damage)+healpercentage(<1)
 * slow - speedpercentage(<1)
 * speed - speedpercentage(>1)
 * weaken - damagepercentage(<1)
 * strength - damagepercentage(>1)
 * damage - hit the target
 * heal - heal the target
 * healing - ticking(heal)
 * ticking(effect) - do effect over interval
 * push - forced move in a direction
 * percentage effects:
 * speed(how much target can move)
 * damage(amount of damage that target can deal)
 * heal(the amount of healing that target can receive from sources)
 * reload(how fast target can reload their weapon)
 * shield(how much damage target receives)
 * immunity(all effects of a certain type will be removed)
 * block(all effects of a certain type will not take effect)
 */
/**
 * Something attached to a GridEntity that is ticked when it updates.
 * @author MinecraftJack64
 */
public abstract class Effect  
{
    private GridEntity target;// the GE this effect is attached to, it should call affect when it updates. cannot be null
    private GridObject source;// the GO that created this effect. can be null
    private EffectID id;// associated effect id. nullability unknown
    private int collisionProtocol = 0;
    public Effect(EffectID id){
        setID(id);
    }
    //tick this effect, usually affecting the attached gridentity
    public abstract void affect();
    //notify this when applied to a GridEntity
    public void appliedTo(GridEntity source){
        setTarget(source);
    }
    //remove this effect from the gridentity to which this is attached
    public void clear(){
        getTarget().clearEffect(this);
        setTarget(null);
    }
    //return the id associated with this effect
    public EffectID getID(){
        return id;
    }
    public GridEntity getTarget(){
        return target;
    }
    public GridObject getSource(){
        return source;
    }
    public void setID(EffectID id){
        this.id = id;
    }
    public void setTarget(GridEntity targ){
        target = targ;
    }
    public void setSource(GridObject source){
        this.source = source;
    }
    //if this effect is currently applied
    public boolean isApplied(){
        return getTarget()!=null;
    }
    public boolean isMalicious(){
        return false;
    }
    //0-3, independent(codominant), stack(incomplete), override(dominant), fail(recessive)
    public int getCollisionProtocol(){
        return collisionProtocol;
    }
    public Effect setCollisionProtocol(int cp){
        collisionProtocol = cp;
        return this;
    }
    public boolean equals(Object o){
        if(!getClass().equals(o.getClass())){
            return false;
        }
        return getID().equals(((Effect)o).getID());
    }
    public void stack(Effect other){}
}
