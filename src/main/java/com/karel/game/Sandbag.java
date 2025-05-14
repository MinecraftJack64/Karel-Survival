package com.karel.game;
/**
 * Write a description of class Sandbag here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sandbag extends GridEntity
{
    private int speed = 19;
    private int decayDelay = 5;
    public Sandbag(double r, GridObject source){
        startHealth(120);
        setRealRotation(r);
        inherit(source);
    }
    public void kAct(){
        if(speed>0){
            move(getRealRotation(), speed);
            speed-=2;
        }else{
            if(decayDelay<=0){
                hit(1, this);
                decayDelay = 5;
            }else decayDelay--;
        }
        super.kAct();
    }
    public boolean willNotify(GridObject source){
        return false;
    }
    public boolean canDetect(){return false;}
}
