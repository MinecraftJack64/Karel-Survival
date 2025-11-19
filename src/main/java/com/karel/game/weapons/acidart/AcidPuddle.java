package com.karel.game.weapons.acidart;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Puddle;
import com.karel.game.effects.ReloadPercentageEffect;

/**
 * Write a description of class FirePuddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AcidPuddle extends Puddle
{
    public AcidPuddle(GridObject source){
        super(200, 40, 4, source);
        setImage("Symbols/Effects/infection.png");
        scaleTexture(80);
        setDamage(200);
        setSelfHit(true);
    }
    public void doHit(GridEntity t){
        if(isAlliedWith(t)){
            t.applyEffect(new ReloadPercentageEffect(1.5, 40, this));
        }
        else super.doHit(t);
    }
    public boolean covertDamage(){
        return true;
    }
}
