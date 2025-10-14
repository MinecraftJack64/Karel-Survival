package com.karel.game.gridobjects.gridentities.zombies.chocolate;

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
public class ChocolatePuddle extends Puddle
{
    public ChocolatePuddle(boolean isSuper, GridObject source){
        super(isSuper?200:30, 1, isSuper?3:60, source);
        setImage("Weapons/fastfood/projPuddle.png");
        scaleTexture(80);
        setDamage(0);
        setHitAllies(true);
    }
    public void doHit(GridEntity g){
        if(isAggroTowards(g)){
            g.applyEffect(new SpeedPercentageEffect(0.75, 2, this));
        }else if(g.getEntityID().equals("zombie-chocolate")){
            g.applyEffect(new SpeedPercentageEffect(2, 2, this));
        }
        super.doHit(g);
    }
}
