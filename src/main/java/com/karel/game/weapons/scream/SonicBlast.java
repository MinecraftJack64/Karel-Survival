package com.karel.game.weapons.scream;

import com.karel.game.Bullet;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.SoftPullEffect;
import com.karel.game.weapons.EffectID;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SonicBlast extends Bullet
{
    
    public SonicBlast(double rotation, boolean isSuper, GridObject source)
    {
        super(rotation, source);
        setSpeed(20);
        setLife(30);
        setDamage(40);
        setNumTargets(-1);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        targ.applyEffect(new SoftPullEffect(getDirection(), 3.5, 20, this, new EffectID("screamwave")));
    }
}
