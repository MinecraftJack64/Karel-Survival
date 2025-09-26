package com.karel.game.weapons.drone;

import com.karel.game.GridObject;
import com.karel.game.Puddle;

/**
 * Write a description of class FirePuddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScorchPuddle extends Puddle
{
    public ScorchPuddle(GridObject source){
        super(100, 30, 5, source);
        setImage("Weapons/inferno/projTrail.png");
        scaleTexture(80);
        setDamage(20);
    }
}
