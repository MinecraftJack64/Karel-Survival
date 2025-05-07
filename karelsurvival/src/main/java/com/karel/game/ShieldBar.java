package com.karel.game;

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
        super(max, size, height, new Color(26, 148, 229));//BLUE
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
    public void act(){
        super.act();
        if(myGE!=null){setRealLocation(myGE.getRealX(), myGE.getRealY()-50-10*myid, myGE.getRealHeight());
        if(myGE.isDead())getWorld().removeObject(this);}
    }
}
