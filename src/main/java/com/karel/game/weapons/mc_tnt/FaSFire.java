package com.karel.game.weapons.mc_tnt;

import com.karel.game.GridObject;
import com.karel.game.Puddle;

/**
 * Write a description of class FirePuddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FaSFire extends Puddle
{
    public FaSFire(GridObject source){
        super(50, 30, 3, source);
        setImage("Weapons/inferno/projTrail.png");
        scaleTexture(80);
        setDamage(100);
    }
    public void tick(){
        for(TNT g: getGOsInRange(getRange(), TNT.class)){
            g.light();
        }
        super.tick();
    }
}
