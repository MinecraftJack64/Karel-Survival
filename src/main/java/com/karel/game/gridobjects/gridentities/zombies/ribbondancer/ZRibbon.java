package com.karel.game.gridobjects.gridentities.zombies.ribbondancer;

import com.karel.game.Boomerang;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.PullEffect;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZRibbon extends Boomerang
{
    private GridEntity targ;
    EffectID sourceStun, targStun;
    int tts = 0;
    
    public ZRibbon(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/zbullet.png");
        setLife(40);
        setSpeed(13);
        setReturnSpeed(15);
        setDamage(50);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(!targ.isDead()){
            this.targ = targ;
            targStun = new EffectID(targ, "ribbon");
            targ.stun(targStun);
            sourceStun = new EffectID(getSource(), "ribbon");
            if(getSource() instanceof GridEntity source){
                source.stun(sourceStun);
            }
        }
    }
    public void doReturn(){
        if(!targ.isDead()){
            targ.pullTo(getX(), getY(), this);
        }else
        {
            dieForReal();
            return;
        }
        if(!((GridEntity)getSource()).isDead()){
            getSource().pullTowards(this, getReturnSpeed());
        }else{
            dieForReal();
            return;
        }
        tts++;
        super.doReturn();
    }
    public void dieForReal(){
        targ.unstun(targStun);
        ((GridEntity)getSource()).unstun(sourceStun);
        targ.applyEffect(new PullEffect(getDirection(), getReturnSpeed(), tts, this));
        ((GridEntity)getSource()).applyEffect(new PullEffect(getDirection()+180, getReturnSpeed(), tts, this));
        super.dieForReal();
    }
}
