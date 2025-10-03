package com.karel.game.weapons.salicycle;

import com.karel.game.GridEntity;
import com.karel.game.MetalShield;
import com.karel.game.Pet;
import com.karel.game.weapons.ShieldID;

public class SaliSwab extends Pet{
    double dir;
    int dieCooldown;
    public SaliSwab(double direction, GridEntity g){
        super(g);
        inherit(g);
        dir = direction;
        dieCooldown = 200;
        setSpeed(4);
        startHealthShield(new MetalShield(new ShieldID(this), 4));
    }
    public void behave(){
        int amt = explodeOn(75, 20);
        walk(dir, amt==0?1:0.4);
        dieCooldown--;
        if(dieCooldown<=0){
            kill(this);
        }
    }
}
