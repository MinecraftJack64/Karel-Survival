package com.karel.game.ui.bars;

import com.karel.game.*;
import com.raylib.Color;

/**
 * A StatusBar that appears above a GridEntity and tracks health.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealthBar extends StatusBar
{
    GridEntity myGE;
    public HealthBar(int max, int size, int height)
    {
        super(max, max, size, height);
    }
    public HealthBar(int max, int size, int height, Color c)
    {
        super(max, max, size, height, c);
    }
    public HealthBar(int max, int size, int height, GridEntity of)
    {
        super(max, max, size, height);
        myGE = of;
    }
    public HealthBar(int max, int size, int height, Color c, GridEntity of)
    {
        super(max, max, size, height, c);
        myGE = of;
    }
    public void setValue(int val){
        if(val<0)super.setValue(0);
        else super.setValue(val);
    }
    public void update(){
        super.update();
        if(myGE!=null){
            if(myGE.isDead())getWorld().removeObject(this);
        }
    }
    public boolean isInGridWorld(){
        return true;
    }
}
