package com.karel.game.gridobjects.gridentities.zombies.exorcist;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZExorcistShot extends ZBullet
{
    
    public ZExorcistShot(double rotation, GridObject source, boolean hitself)
    {
        super(rotation, source);
        setDamage(35);
        setLife(20);
        setSpeed(15);
        if(hitself){
            setNumTargets(1);
            setDamage(35);
        }
        else
            setNumTargets(-1);
        //setAggression(false);
        setSelfHarm(hitself);
        setHitAllies(true);
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void doHit(GridEntity target){
        if(isAggroTowards(target)){
            super.doHit(target);
        }
        if(isAlliedWith(target)&&!(target instanceof DemonZombie)){
            heal(target, getDamage());
            if(!target.isDead()&&getSource()!=target){
                DemonZombie d = new DemonZombie(target);
                target.addObjectHere(d);
            }
        }
    }
}
