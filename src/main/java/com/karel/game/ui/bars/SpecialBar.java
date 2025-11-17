package com.karel.game.ui.bars;

import com.raylib.Color;
import com.raylib.Raylib;
/**
 * Write a description of class AmmoBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpecialBar extends AmmoBar
{
    public static final Color COLOR = Raylib.YELLOW;
    public static final Color SUPER_COLOR = Raylib.SKYBLUE;
    public static final Color DISABLED_COLOR = Raylib.RED;
    public SpecialBar(int ammo, int max, int size, int height)
    {
        super(ammo, max, size, height);
        setColor(COLOR);
    }
    public SpecialBar(int ammo, int max, int size, int height, int phases)
    {
        super(ammo, max, size, height, phases);
        setColor(COLOR);
    }
    public void disable(){
        setColor(DISABLED_COLOR);
    }
    public void reset(){
        setColor(COLOR);
    }
    public void setValue(int amt){
        super.setValue(amt);
        if(amt>getMax()){
            setColor(SUPER_COLOR);
        }else{
            setColor(COLOR);
        }
    }
}
