package com.karel.game.ui.bars;

import com.karel.game.GridEntity;
import com.raylib.Color;
/**
 * Write a description of class BadShieldBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BadShieldBar extends ShieldBar
{
    public BadShieldBar(int max, int size, int height, int id, GridEntity of)
    {
        super(-max, size, height, id, of);//BLUE
        setColor(new Color((byte)0, (byte)-127, (byte)-127, (byte)1));
    }
    public void setValue(int val){
        if(val>=0)getWorld().removeObject(this);
        else super.setValue(-val);
    }
}
