package com.karel.game;
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SonicBlast extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    private boolean isSuper;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public SonicBlast(double rotation, boolean isSuper, GridObject source)
    {
        super(rotation, source);
        setSpeed(20);
        setLife(30);
        setDamage(40);
        setNumTargets(-1);
        this.isSuper = isSuper;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        targ.applyEffect(new SoftPullEffect(getDirection(), 3.5, 20, this, new EffectID("screamwave")));
    }
}
