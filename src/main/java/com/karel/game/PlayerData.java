package com.karel.game;

public class PlayerData {
    // CURRENCIES
    private int NUM_FRAGS = 1000;
    public PlayerData(){
        //
    }
    public void setNumFrags(int amt){
        NUM_FRAGS = amt;
        //TODO: Update currency listeners
    }
    public int getNumFrags(){
        return NUM_FRAGS;
    }
}
