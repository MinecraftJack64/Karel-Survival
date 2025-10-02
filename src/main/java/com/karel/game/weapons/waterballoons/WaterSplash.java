package com.karel.game.weapons.waterballoons;
import java.util.HashSet;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.DamageExposureEffect;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class WaterSplash extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public WaterSplash(double rotation, GridObject source)
    {
        super(rotation, new HashSet<GridEntity>(), source);
    }
    public WaterSplash(double rotation, HashSet<GridEntity> h, GridObject source)
    {
        super(rotation, h, source);
        setSpeed(17);
        setLife(11);
        setDamage(30);
        setNumTargets(-1);
        setMultiHit(false);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        targ.applyEffect(new DamageExposureEffect(2, 30, getSource()));
    }
}
