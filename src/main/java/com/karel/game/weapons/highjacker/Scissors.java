package com.karel.game.weapons.highjacker;

import com.karel.game.Boomerang;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Scissors extends Boomerang
{
    
    public Scissors(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(27);
        setReturnSpeed(25);
        setNumTargets(-1);
        setDamage(115);
        setLife(4);
        setDamageOnReturn(1);
    }
    public void doHit(GridEntity targ){
        //
        super.doHit(targ);
    }
}
