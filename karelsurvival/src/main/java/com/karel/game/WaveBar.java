package com.karel.game;

/**
 * Write a description of class WaveBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaveBar extends LifeBar
{
    public WaveBar(int max, int size, int height)
    {
        super(max, size, height);
    }
    public WaveBar(int max, int size, int height, GridEntity of)
    {
        super(max, size, height);
    }
    public boolean isInGridWorld(){
        return false;
    }
}
