package com.karel.game;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZHealShot extends ZBullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 300;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 150;
    
    public ZHealShot(double rotation, GridObject source, boolean hitself)
    {
        super(rotation, source);
        setDamage(50);
        setLife(20);
        setSpeed(15);
        if(hitself)
            setNumTargets(1);
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
        if(isAlliedWith(target)){
            heal(target, getDamage());
        }
    }
}
