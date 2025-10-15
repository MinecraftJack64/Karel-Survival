package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * Works like PullEffect, but does not stun the target, allowing them to continue using abilities and moving.
 * ID "soft_pull"
 * @author MinecraftJack64
 */
public class SoftPullEffect extends DurationEffect
{
    private double direction, speed;
    public SoftPullEffect(double direction, double speed, int duration, GridObject source){
        super(duration, source);
        this.direction = direction;
        this.speed = speed;
    }
    public SoftPullEffect(double direction, double speed, int duration, GridObject source, EffectID id){
        super(duration, source, id);
        this.direction = direction;
        this.speed = speed;
    }
    public SoftPullEffect(double direction, double speed, int duration, EffectID id){
        super(duration, id);
        this.direction = direction;
        this.speed = speed;
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/soft_pull.png";
    }
    public void affect(){
        getTarget().pull(direction, speed, getSource());
        super.affect();
    }
    public int getCollisionProtocol(){
        return 2;
    }
    public String getStringID(){return "soft_pull";}
}
