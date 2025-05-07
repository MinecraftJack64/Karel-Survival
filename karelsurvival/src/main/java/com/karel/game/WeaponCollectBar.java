package com.karel.game;

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
        super(ammo, max, size, height, Color.GRAY);
    }
    public WeaponCollectBar(int ammo, int max, int size, int height, int phases)
    {
        super(ammo, max, size, height, Color.GRAY);
        divideIntoPhases(phases);
    }
    public void disable(){
        setColor(Color.RED);
    }
    public void reset(){
        setColor(Color.GRAY);
    }
    /*public void update(){
        if(!getColor().equals(Color.ORANGE.darker()))setColor(Color.ORANGE.darker());
        else super.update();
    }*/
}
