package com.karel.game.weapons.grenade;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;

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
        setRotation(r);
        inherit(source);
    }
    public void update(){
        if(speed>0){
            move(getRotation(), speed);
            speed-=2;
        }else{
            if(decayDelay<=0){
                hit(1, this);
                decayDelay = 5;
            }else decayDelay--;
        }
        super.update();
    }
    public boolean willNotify(GridObject source){
        return false;
    }
    public boolean canDetect(){return false;}
    public String getEntityID(){return "pet-grenade-sandbag";}
}
