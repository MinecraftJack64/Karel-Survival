package com.karel.game.ui.bars;

import static com.raylib.Raylib.GRAY;
import static com.raylib.Raylib.RED;

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
        super(ammo, max, size, height, GRAY);
    }
    public WeaponCollectBar(int ammo, int max, int size, int height, int phases)
    {
        super(ammo, max, size, height, GRAY);
        divideIntoPhases(phases);
    }
    public void disable(){
        setColor(RED);
    }
    public void reset(){
        setColor(GRAY);
    }
    /*public void update(){
        if(!getColor().equals(Color.ORANGE.darker()))setColor(Color.ORANGE.darker());
        else super.update();
    }*/
}
