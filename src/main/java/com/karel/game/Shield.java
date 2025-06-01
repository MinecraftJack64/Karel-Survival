package com.karel.game;

import com.karel.game.weapons.ShieldID;

/**
 * Write a description of class Shield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shield  
{
    GridEntity myGE;
    ShieldID myID;
    public Shield(ShieldID id){
        myID = id;
    }
    public int processDamage(int dmg, GridObject source){
        return dmg;
    }
    public boolean damage(int amt, GridObject s){return false;}//return true if shield breaks
    public void tick(){}
    public void remove(){
        getHolder().removeShield(getID());
    }
    public int getHealth(){
        return 100;
    }
    public boolean canBreak(){
        return false;
    }
    public boolean canStack(){
        return false;
    }
    public GridEntity getHolder(){
        return myGE;
    }
    public ShieldID getID(){
        return myID;
    }
    public void setHolder(GridEntity g){
        myGE = g;
    }
    public void applyTo(GridEntity g){
        setHolder(g);
    }
}


