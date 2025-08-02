package com.karel.game.weapons.catclaw;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.SoftPullEffect;
import com.karel.game.weapons.EffectID;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class PullingClaw extends Claw
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public PullingClaw(double rotation, GridObject source)
    {
        super(rotation, source);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        targ.applyEffect(new SoftPullEffect(face(getSource(), false), 4, 10, this, new EffectID("catclaw")));
    }
}
