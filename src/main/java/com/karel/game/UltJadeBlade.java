package com.karel.game;

import com.karel.game.effects.EffectID;
import com.karel.game.effects.SizePercentageEffect;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class UltJadeBlade extends JadeBlade
{
    
    public UltJadeBlade(double rotation, int size, boolean isUpgrade, GridObject source)
    {
        super(rotation, size, isUpgrade, source);
    }
    public void doHit(GridEntity targ){
        targ.applyEffect(new SizePercentageEffect(0.70, -1, this, new EffectID(this)));
        super.doHit(targ);
    }
}
