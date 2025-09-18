package com.karel.game.weapons.lymphcannon;

import com.karel.game.gridobjects.hitters.Bullet;

public class Cytokine extends Bullet {
    CellTurret target;
    String newTarget;
    public Cytokine(CellTurret t, String target){
        this.target = t;
        newTarget = target;
    }
    public void applyPhysics(){
        move(face(target, true), 20);
        if(distanceTo(target)<20){
            target.gadget(newTarget);
            die();
            getWorld().removeObject(this);
        }
    }
}
