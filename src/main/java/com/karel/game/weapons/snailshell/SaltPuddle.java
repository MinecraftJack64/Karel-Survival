package com.karel.game.weapons.snailshell;

import com.karel.game.GridObject;
import com.karel.game.Puddle;

/**
 * Write a description of class FirePuddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SaltPuddle extends Puddle
{
    public SaltPuddle(GridObject source){
        super(50, 30, 3, source);
        setImage("Weapons/inferno/projTrail.png");
        scaleTexture(80);
        setDamage(200);
    }
}
