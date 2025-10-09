package com.karel.game.weapons.salicycle;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;

public class SuperSalicylicBaton extends SalicylicBaton {
    public SuperSalicylicBaton(double dir, int range, GridObject source){
        super(dir, range, source);
    }
    public void doHit(GridEntity t){
        t.addObjectHere(new Scab(Greenfoot.getRandomNumber(360), Greenfoot.getRandomNumber(75), this));
        super.doHit(t);
    }
}
