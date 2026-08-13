package com.karel.game.gridobjects.gridentities.zombies.supernova;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZDyingStar extends Bullet
{
    
    public ZDyingStar(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(3);
        setLife(175);
        setDamage(140);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        for(int i = -35; i <= 35; i+=5){
            ZSupernovaMatter w = new ZSupernovaMatter(i+getDirection(), getHitStory(), this);
            addObjectHere(w);
        }
    }
    public void expire(){
        for(int i = -35; i <= 35; i+=5){
            ZSupernovaMatter w = new ZSupernovaMatter(i+getDirection(), getHitStory(), this);
            addObjectHere(w);
        }
        super.expire();
    }
}
