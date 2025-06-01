package com.karel.game;

import com.karel.game.weapons.EffectID;

/**
 * Temporarily immobilizes the target, preventing movement abilities.
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
    public void affect(){
        getTarget().pull(direction, speed);
        super.affect();
    }
    public int getCollisionProtocol(){
        return 2;
    }
}
