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
    int bonus = 0;
    boolean isUpgrade;
    public Scissors(double rotation, GridObject source, boolean upgrade)
    {
        super(rotation, source);
        setSpeed(27);
        setReturnSpeed(25);
        setNumTargets(-1);
        setDamage(115);
        setLife(4);
        setDamageOnReturn(1);
        isUpgrade = upgrade;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        damage(targ, bonus*25);
        if(isUpgrade)bonus++;
    }
    public void startReturn(){
        bonus = 0;
        super.startReturn();
    }
}
