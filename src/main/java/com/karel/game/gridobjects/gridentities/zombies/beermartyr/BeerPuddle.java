package com.karel.game.gridobjects.gridentities.zombies.beermartyr;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Puddle;
import com.karel.game.effects.SpeedPercentageEffect;

/**
 * Write a description of class FirePuddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BeerPuddle extends Puddle
{
    public BeerPuddle(GridObject source){
        super(80, 30, 4, source);
        setImage("Weapons/inferno/projTrail.png");
        scaleTexture(80);
        setDamage(0);
        setHitAllies(true);
    }
    public void doHit(GridEntity targ){
        if(isAlliedWith(targ)){
            heal(targ, 100);
        }
        if(isAggroTowards(targ)){
            targ.applyEffect(new SpeedPercentageEffect(0.5, 30, this));
        }
        super.doHit(targ);
    }
}
