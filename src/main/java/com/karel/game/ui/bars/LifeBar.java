package com.karel.game.ui.bars;

import com.karel.game.GridEntity;
import com.raylib.Color;
import com.raylib.Raylib;

/**
 * Write a description of class LifeBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LifeBar extends HealthBar
{
    GridEntity myGE;
    public LifeBar(int max, int size, int height)
    {
        super(max, size, height);
    }
    public LifeBar(int max, int size, int height, GridEntity of)
    {
        super(max, size, height, of);
        myGE = of;
    }
    public void setValue(int val){
        if(val<0)super.setValue(0);
        else super.setValue(val);
        while(phases.size()>0&&getValue()<phases.get(phases.size()-1)){
            phases.remove(phases.size()-1);
            update();
        }
        double perc = getPerc();
        if(perc>1){
            setColor(Raylib.BLUE);
        }else
            setColor(new Color(perc>=0.5?(byte)(255*(1-perc)*2):-1, perc<=0.5?(byte)(255*(perc)*2):-1, (byte)0, (byte)-1));
    }
    public void update(){
        super.update();
        if(myGE!=null){
            setLocation(myGE.getX(), myGE.getY()-40, myGE.getHeight());
            if(myGE.isDead())getWorld().removeObject(this);
        }
    }
}
