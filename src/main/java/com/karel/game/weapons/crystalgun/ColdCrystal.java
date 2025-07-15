package com.karel.game.weapons.crystalgun;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * Write a description of class Crystal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ColdCrystal extends Crystal
{
    public ColdCrystal(GridEntity target, GridObject source){
        super(target, source);
    }
    public void behave(){
        setExposure(Math.pow(0.8, getGEsInRange(150).stream().filter(e->e instanceof ColdCrystal te&&te.getSource()==getSource()).count()));
        super.behave();
    }
}
