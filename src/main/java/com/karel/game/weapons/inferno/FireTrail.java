package com.karel.game.weapons.inferno;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Puddle;
import com.karel.game.effects.BurnEffect;

/**
 * Write a description of class FireTrail here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FireTrail extends Puddle
{
    public FireTrail(GridObject source){
        super(30, 15, 3, source);
        setImage("Weapons/inferno/projTrail.png");
        setOpacity(127);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        targ.applyEffect(new BurnEffect(5, 15, getTimes(), getSource()));
    }
}
