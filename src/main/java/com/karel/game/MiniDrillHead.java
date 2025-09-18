package com.karel.game;

import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class MiniDrillHead extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public MiniDrillHead(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(10);
        setLife(60);
        setDamage(200);
        setNumTargets(-1);
    }
    
    public void doHit(GridEntity targ){
        targ.applyEffect(new SpeedPercentageEffect(0.5, 60, this));
        super.doHit(targ);
    }
}
