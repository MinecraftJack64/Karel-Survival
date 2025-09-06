package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * For a preset duration, stuns the target and pulls them in a specified direction at a specified speed.
 * ID "pull"
 * @author MinecraftJack64
 */
public class PullEffect extends DurationEffect
{
    private double direction, speed;
    public PullEffect(double direction, double speed, int duration, GridObject source){
        super(duration, source);
        this.direction = direction;
        this.speed = speed;
    }
    public PullEffect(double direction, double speed, int duration, GridObject source, EffectID id){
        super(duration, source, id);
        this.direction = direction;
        this.speed = speed;
    }
    public PullEffect(double direction, double speed, int duration, EffectID id){
        super(duration, id);
        this.direction = direction;
        this.speed = speed;
    }
    public void affect(){
        getTarget().pull(direction, speed);
        super.affect();
    }
    public void onClear(){
        getTarget().unstun(getID());
    }
    public void onApply(){
        getTarget().stun(getID());
    }
    public int getCollisionProtocol(){
        return 2;
    }
}
