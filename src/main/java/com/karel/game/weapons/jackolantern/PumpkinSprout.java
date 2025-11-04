package com.karel.game.weapons.jackolantern;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

public class PumpkinSprout extends Bullet{
    private int growingCooldown = 30;
    public PumpkinSprout(double r, GridObject source){
        super(r, source);
        setSpeed(10);
        setDamage(50);
        setLife(30);
        setNumTargets(1);
    }
    public void applyPhysics(){
        if(growingCooldown<=0)super.applyPhysics();
        else growingCooldown--;
    } // TODO: watermelon graphics
    public boolean covertDamage(){
        return false;
    }
}
