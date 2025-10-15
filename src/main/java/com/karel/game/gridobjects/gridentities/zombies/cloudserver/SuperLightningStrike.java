package com.karel.game.gridobjects.gridentities.zombies.cloudserver;

import com.karel.game.GridObject;
import com.karel.game.LightningStrike;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SuperLightningStrike extends LightningStrike
{
    public SuperLightningStrike()
    {
        super(null);
    }
    public SuperLightningStrike(GridObject source)
    {
        super(source);
        setHeight(4000);
        setDamage(500);
    }
}
