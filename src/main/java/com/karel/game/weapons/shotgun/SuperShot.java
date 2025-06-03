package com.karel.game.weapons.shotgun;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SuperShot extends Shot
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public SuperShot(double rotation, GridObject source)
    {
        super(rotation, source);
        setNumTargets(5);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        setSpeed(getSpeed()/2);
    }
}
