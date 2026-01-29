package com.karel.game.weapons.lanternlobber;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Puddle;
import com.karel.game.effects.DamageExposureEffect;
import com.karel.game.effects.SpeedPercentageEffect;
/**
 * Write a description of class WaterPuddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LanternPuddle extends Puddle
{
    public LanternPuddle(int duration, GridObject source){
        super(100, 1, duration, source);
        setImage("Weapons/hearth/proj.png");
        scaleTexture(100);
        setDamage(0);
    }
    public void doHit(GridEntity g){
        g.applyEffect(new SpeedPercentageEffect(0.75, 2, this));
        g.applyEffect(new DamageExposureEffect(1.25, 2, this));
        super.doHit(g);
    }
}
