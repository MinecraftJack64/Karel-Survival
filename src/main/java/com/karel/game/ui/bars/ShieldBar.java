package com.karel.game.ui.bars;

import com.raylib.Raylib;

import com.karel.game.GridEntity;

/**
 * Write a description of class ShieldBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShieldBar extends HealthBar
{
    GridEntity myGE;
    private int myid = 0;
    /*public ShieldBar(int max, int size, int height)
    {
        super(max, max, size, height);
    }*/
    public ShieldBar(int max, int size, int height, int id, GridEntity of)
    {
        super(max, size, height, Raylib.BLUE);//BLUE
        myGE = of;
        myid = id;
    }
    public void setValue(int val){
        if(val<=0)getWorld().removeObject(this);
        else super.setValue(val);
    }
    public void updateID(int n){
        myid = n;
    }
    public void update(){
        super.update();
        if(myGE!=null){setLocation(myGE.getX(), myGE.getY()-50-10*myid, myGE.getHeight());
        if(myGE.isDead())getWorld().removeObject(this);}
    }
}
