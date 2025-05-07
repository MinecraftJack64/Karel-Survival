package com.karel.game;
/**
 * An effect that lasts a specified number of frames, removing itself after.
 * @author MinecraftJack64
 */
public class DurationEffect extends Effect
{
    int duration;
    public DurationEffect(int duration, GridObject source){
        this(duration, source, new EffectID(source));
    }
    public DurationEffect(int duration, GridObject source, EffectID id){
        super(id);
        this.duration = duration;
        setSource(source);
        setCollisionProtocol(1);
    }
    public DurationEffect(int duration, EffectID id){
        super(id);
        this.duration = duration;
        setCollisionProtocol(1);
    }
    public int getDuration(){
        return duration;
    }
    public void setDuration(int t){
        duration = t;
    }
    public void affect(){
        duration--;
        if(duration==0){
            clear();
        }
    }
    public void appliedTo(GridEntity source){
        super.appliedTo(source);
        onApply();
    }
    public void clear(){
        onClear();
        super.clear();
    }
    public void onApply(){}
    public void onClear(){}
    public void stack(Effect other){
        duration+=((DurationEffect)other).getDuration();
    }
}
