package com.karel.game.weapons.soup;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.HealEffect;
import com.karel.game.effects.PowerPercentageEffect;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.collectibles.Collectible;

/**
 * a collectible usually dropped from zombies when killed that can be collected by the player, healing them
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class SoupBoost extends Collectible
{
    private double focus;
    public SoupBoost(double fc, GridObject source)
    {
        this.focus = fc;
        inherit(source);
    }
    public void collect(GridObject t){
        GridEntity targ = (GridEntity) t;
        int duration = 40+(int)(40*focus), perc = 1+(int)(0.25*focus);
        targ.applyEffect(new SpeedPercentageEffect(perc, duration, this));
        targ.applyEffect(new PowerPercentageEffect(perc, duration, this));
        //0.5 focus: 1.125 duration 60, 2 focus: 1.5 duration 140
        focus = 2.5-focus;
        targ.applyEffect(new HealEffect((int)(34+33*focus), 25, 4+(int)focus, this));
        //2 focus: 50*4, 0.5 focus: 100*6
        super.collect(targ);
    }
}
