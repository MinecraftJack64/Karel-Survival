package com.karel.game.weapons.grimoire;

import com.karel.game.GridObject;
import com.karel.game.Puddle;

/**
 * Write a description of class FirePuddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MagicFirePuddle extends Puddle
{
    public MagicFirePuddle(GridObject source){
        super(80, 30, 3, source);
        setImage("Weapons/inferno/projTrail.png");
        scaleTexture(80);
        setDamage(50);
    }
}
