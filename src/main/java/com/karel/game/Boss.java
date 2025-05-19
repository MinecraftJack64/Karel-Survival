package com.karel.game;
public class Boss extends Zombie{
    public Boss(){
        scaleTexture(90, 90);
    }
    public int getPhase(){
        return 1;
    }
}