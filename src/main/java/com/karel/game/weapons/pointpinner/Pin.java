package com.karel.game.weapons.pointpinner;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Pin extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    private Pointpinner me;
    public Pin(double rotation, GridObject source, Pointpinner p)
    {
        super(rotation, source);
        setSpeed(0.9);
        setLife(31);
        setDamage(40);
        me = p;
    }
    public Pin(double rotation, GridObject source, double startingSpeed)
    {
        this(rotation, source, null);
        setSpeed(startingSpeed);
        setLife(getLife()- (int)startingSpeed);
    }
    public void applyPhysics(){
        super.applyPhysics();
        setDamage(getDamage()+(int)getSpeed());
        if(getSpeed()<=31){
            setSpeed(getSpeed()+1);
        }
    }
    public void die(){
        super.die();
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(me!=null)me.notifyHit();
    }
}
