package com.karel.game.ui.bars;

import com.karel.game.GridEntity;
import com.raylib.Color;
import static com.raylib.Raylib.*;

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
            setColor(BLUE);
        }else
            setColor(new Color(perc>=0.5?(byte)(255*(1-perc)*2-128):127, perc<=0.5?(byte)(255*(perc)*2-128):127, (byte)0, (byte)1));
    }
    public void update(){
        super.update();
        if(myGE!=null){
            setRealLocation(myGE.getRealX(), myGE.getRealY()-40, myGE.getRealHeight());
            if(myGE.isDead())getWorld().removeObject(this);
        }
    }
}
