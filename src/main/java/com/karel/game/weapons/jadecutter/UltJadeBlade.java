package com.karel.game.weapons.jadecutter;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.SizePercentageEffect;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class UltJadeBlade extends JadeBlade
{
    
    public UltJadeBlade(double rotation, double size, boolean isUpgrade, GridObject source)
    {
        super(rotation, size, isUpgrade, source);
    }
    public void doHit(GridEntity targ){
        if(!targ.hasEffect(SizePercentageEffect.class))targ.applyEffect(new SizePercentageEffect(0.70, -1, this, new EffectID(this)));
        super.doHit(targ);
    }
}
