package com.karel.game.weapons.waterballoons;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.DamageExposureEffect;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class WaterBalloon extends Bullet
{
    private boolean isSuper, isUlt;
    
    public WaterBalloon(double rotation, GridObject source, boolean isSuper, boolean isUlt)
    {
        super(rotation, source);
        setSpeed(25);
        setLife(15);
        setDamage(140);
        this.isSuper = isSuper;
        this.isUlt = isUlt;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        targ.applyEffect(new DamageExposureEffect(2, isUlt?100:30, this));
        for(int i = -35; i <= 35; i+=10){
            WaterSplash w = new WaterSplash(i+getDirection(), getHitStory(), this);
            addObjectHere(w);
        }
        if(isSuper){
            for(int i = -30; i <= 30; i+=20){
                SuperWaterSplash w = new SuperWaterSplash(i+getDirection(), getHitStory(), this);
                addObjectHere(w);
            }
        }
    }
    public void expire(){
        for(int i = -35; i <= 35; i+=10){
            WaterSplash w = new WaterSplash(i+getDirection(), getHitStory(), this);
            addObjectHere(w);
        }
        if(isSuper){
            for(int i = -30; i <= 30; i+=20){
                SuperWaterSplash w = new SuperWaterSplash(i+getDirection(), getHitStory(), this);
                addObjectHere(w);
            }
        }
        super.expire();
    }
}
