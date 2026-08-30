package com.karel.game.gridobjects;

import com.karel.game.GridObject;

/*
 * Tinkerer found in middle of tinker menu room. Not in regular gameplay
 */
public class Tinkerer extends GridObject{
    int expecting;
    boolean ready;
    public Tinkerer(){
        //TODO
    }
    public void receiveFrag(){
        //TODO: show particle swirling around
        expecting--;
        if(ready){
            ready = false;
            tinker();
        }
    }
    public void tinker(){
        //
    }
    public void expect(int amt){
        ready = true; // lever down TODO
        expecting = amt;
    }
    public boolean saveable(){
        return false;
    }
}
