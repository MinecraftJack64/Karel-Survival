package com.karel.game.weapons.jackolantern;

import com.karel.game.Boomerang;
import com.karel.game.GridObject;

public class SmallPumpkinGhost extends Boomerang{
    public SmallPumpkinGhost(double r, GridObject source){
        super(r, source);
        setDamage(50);
        setDamageOnReturn(2);
        setReturnDamage(50);
        setReturnSpeed(9);
        setLife(55);
        setSpeed(9);
        setNumTargets(-1);
    }
}
