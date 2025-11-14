package com.karel.game.weapons.lovestrike;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Heartbreak extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 300;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 150;
    
    public Heartbreak(double rotation, GridObject source)
    {
        super(rotation, source);
        setDamage(35);
        setLife(8);
        setSpeed(15);
        setNumTargets(2);
        //setAggression(false);
        setSelfHit(true);
        setHitAllies(true);
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void doHit(GridEntity target){
        if(isAggroTowards(target)){
            super.doHit(target);
        }
        if(isAlliedWith(target)){
            heal(target, getDamage());
        }
    }
    
    public boolean covertDamage(){
        return true;
    }
}
