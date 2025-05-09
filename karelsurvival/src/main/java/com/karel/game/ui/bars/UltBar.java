package com.karel.game.ui.bars;

import static com.raylib.Raylib.*;

/**
 * Write a description of class UltBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UltBar extends StatusBar
{
    public UltBar(int max, int size, int height)
    {
        super(0, max, size, height, ORANGE);
        setRealRotation(-90);
    }
    public void setValue(int val){
        if(val<=0)val=0;
        super.setValue(val);
        setColor(ORANGE);
    }
}
