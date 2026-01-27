package com.karel.game.playerdata;

import java.io.Serializable;

import com.karel.game.EventListener;

public class PlayerData implements Serializable{
    private EventListener target;
    // CURRENCIES
    private int NUM_FRAGS = 1000;
    // MODES
    public SurvivalData survival;
    public PlayerData(){
        survival = new SurvivalData();
    }
    public void setNumFrags(int amt){
        NUM_FRAGS = amt;
        //TODO: Update currency listeners
        if(target!=null){
            target.on("change", null);
        }
    }
    public int getNumFrags(){
        return NUM_FRAGS;
    }
    public void setEventListener(EventListener t){
        target = t;
    }
}
