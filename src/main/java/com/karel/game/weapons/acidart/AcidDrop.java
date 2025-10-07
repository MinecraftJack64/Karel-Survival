package com.karel.game.weapons.acidart;

import com.karel.game.Dropper;
import com.karel.game.GridObject;

public class AcidDrop extends Dropper {
    public AcidDrop(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, new AcidPuddle(source), source);
        setImage("button-green.png");
        scaleTexture(50);
        setRange(200);
        setDamage(200);
    }
}
