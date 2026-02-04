package com.karel.game.gridobjects.gridentities.zombies.magma;

import com.karel.game.GridObject;
import com.karel.game.Puddle;

/**
 * Write a description of class FirePuddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MagmaPuddle extends Puddle
{
    public MagmaPuddle(GridObject source){
        super(70, 1, 3, source);
        setImage("Weapons/fastfood/projPuddle.png");
        scaleTexture(80);
        setDamage(3);
    }
}
