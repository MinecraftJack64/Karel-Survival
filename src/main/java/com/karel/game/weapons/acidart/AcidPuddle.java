package com.karel.game.weapons.acidart;

import com.karel.game.GridObject;
import com.karel.game.Puddle;

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
    }
    public double damageSecrecy(){
        return super.damageSecrecy()*0.2;
    }
}
