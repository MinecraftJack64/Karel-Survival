package com.karel.game;
import java.util.HashSet;

import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SuperWaterSplash extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public SuperWaterSplash(double rotation, GridObject source)
    {
        super(rotation, new HashSet<GridEntity>(), source);
    }
    public SuperWaterSplash(double rotation, HashSet<GridEntity> h, GridObject source)
    {
        super(rotation, h, source);
        setSpeed(20);
        setLife(11);
        setDamage(20);
        setNumTargets(-1);
        setMultiHit(false);
    }
    public void doHit(GridEntity t){
        t.applyEffect(new SpeedPercentageEffect(0.8, 60, this));
        super.doHit(t);
    }
}
