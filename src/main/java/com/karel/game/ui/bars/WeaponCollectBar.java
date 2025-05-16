package com.karel.game.ui.bars;

import com.raylib.Raylib;

/**
 * Write a description of class WeaponCollectBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WeaponCollectBar extends StatusBar
{
    public WeaponCollectBar(int ammo, int max, int size, int height)
    {
        super(ammo, max, size, height, Raylib.GRAY);
    }
    public WeaponCollectBar(int ammo, int max, int size, int height, int phases)
    {
        super(ammo, max, size, height, Raylib.GRAY);
        divideIntoPhases(phases);
    }
    public void disable(){
        setColor(Raylib.RED);
    }
    public void reset(){
        setColor(Raylib.GRAY);
    }
    /*public void update(){
        if(!getColor().equals(Color.ORANGE.darker()))setColor(Color.ORANGE.darker());
        else super.update();
    }*/
}
