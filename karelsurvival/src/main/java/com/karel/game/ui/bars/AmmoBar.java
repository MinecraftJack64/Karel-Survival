package com.karel.game.ui.bars;

import com.raylib.Color;
import static com.raylib.Raylib.*;
/**
 * Write a description of class AmmoBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AmmoBar extends StatusBar
{
    public static final Color COLOR = ORANGE;
    public static final Color SUPER_COLOR = SKYBLUE;
    public static final Color DISABLED_COLOR = RED;
    public AmmoBar(int ammo, int max, int size, int height)
    {
        super(ammo, max, size, height, COLOR);
    }
    public AmmoBar(int ammo, int max, int size, int height, int phases)
    {
        super(ammo, max, size, height, COLOR);
        divideIntoPhases(phases);
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
