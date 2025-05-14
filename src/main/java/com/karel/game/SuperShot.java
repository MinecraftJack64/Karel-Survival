package com.karel.game;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SuperShot extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public SuperShot(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(17);
        setLife(10);
        setDamage(50);
        setNumTargets(5);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        setSpeed(getSpeed()/2);
    }
}
