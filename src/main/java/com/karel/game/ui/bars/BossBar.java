package com.karel.game.ui.bars;

import com.karel.game.GridEntity;
import com.karel.game.ui.text.TextDisplay;

/**
 * Write a description of class BossBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BossBar extends LifeBar
{
    GridEntity myGE;
    TextDisplay label;
    public BossBar(int max, int size, int height)
    {
        this(max, size, height, null);
    }
    public BossBar(int max, int size, int height, GridEntity of)
    {
        super(max, size, height);
        myGE = of;
        label = new TextDisplay(getLabel(), 30);
    }
    public void notifyWorldAdd(){
        super.notifyWorldAdd();
        if(label==null)return;
        getWorld().addObject(label, getX(), getY());
        setLocation(getX(), label.getBottom());
    }
    public void notifyWorldRemove(){
        if(label!=null)getWorld().removeObject(label);
        super.notifyWorldRemove();
    }
    public String getLabel(){
        return myGE.getName();
    }
    public boolean isInGridWorld(){
        return false;
    }
}
