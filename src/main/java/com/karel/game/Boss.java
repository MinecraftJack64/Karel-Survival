package com.karel.game;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;

public class Boss extends Zombie{
    public Boss(){
        scaleTexture(90, 90);
    }
    public int getPhase(){
        return 1;
    }
}