package com.karel.game.ui.bars;

import com.karel.game.GridEntity;
import com.karel.game.Game;

/**
 * Write a description of class BossBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BossBar extends LifeBar
{
    GridEntity myGE;
    public BossBar(int max, int size, int height)
    {
        super(max, size, height);
    }
    public BossBar(int max, int size, int height, GridEntity of)
    {
        super(max, size, height);
        myGE = of;
    }
    public String getLabel(){
        return myGE.getName();
    }
    public boolean isInGridWorld(){
        return false;
    }
}
