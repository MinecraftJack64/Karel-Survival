package com.karel.game.weapons.salicycle;

import com.karel.game.CoolingMetalShield;
import com.karel.game.GridEntity;
import com.karel.game.Pet;
import com.karel.game.weapons.ShieldID;

public class SaliSwab extends Pet{
    double dir;
    int dieCooldown, amt;
    public SaliSwab(double direction, GridEntity g){
        super(g);
        inherit(g);
        dir = direction;
        dieCooldown = 200;
        setSpeed(4);
        startHealthShield(new CoolingMetalShield(new ShieldID(this), 4, 10));
    }
    public void behave(){
        amt = explodeOn(75, 20);
        walk(dir, !isAttacking()?1:0.4);
        dieCooldown--;
        if(dieCooldown<=0){
            kill(this);
        }
    }
    public boolean isAttacking(){
        return amt>0;
    }
    public boolean canBePulled(){
        return false;
    }
    public boolean acceptHeals(){
        return false;
    }
}
