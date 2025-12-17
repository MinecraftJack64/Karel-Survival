package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * Temporarily stuns the target, stopping their movement and abilities.
 * ID "stun"
 * @author MinecraftJack64
 */
public class BuildupEffect extends DurationEffect
{
    public int maxDuration = 0;
    public BuildupEffect(int duration, int maxDuration, GridObject source){
        super(maxDuration, source);
        setDuration(duration);
        this.maxDuration = maxDuration;
        System.out.println("NEW: "+getDuration()+" "+getMaxDuration());
    }
    public BuildupEffect(int duration, int maxDuration, GridObject source, EffectID id){
        super(maxDuration, source, id);
        setDuration(duration);
        this.maxDuration = maxDuration;
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/stun.png";
    }
    public void affect(){
        if(getDuration()==maxDuration){
            score();
            clear();
        }
        else super.affect();
    }
    @Override
    public boolean updateMaxOnApplication(){
        return false;
    }
    public void score(){
        //
    }
    public int getScore(){
        return getDuration();
    }
    public double getScorePercent(){
        return getDuration()*1.0/maxDuration;
    }
    public void stack(Effect other){
        super.stack(other);
        System.out.println(((BuildupEffect)other).getDuration()+" "+getDuration());
        setDuration(((BuildupEffect)other).getDuration()+getDuration());
        if(getDuration()>maxDuration){
            setDuration(maxDuration);
            System.out.println(maxDuration);
        }
    }
    public String getStringID(){return "buildup";}
}
