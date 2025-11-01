package com.karel.game.weapons.jackolantern;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Hitter;

public class SmashedJack extends Hitter {
    private int ammo, cooldown; // 30
    public SmashedJack(GridObject source){
        super(source);
        ammo = 1;
    }
    public SmashedJack(GridObject source, int a){
        super(source);
        ammo = a;
    }
    public void applyPhysics(){
        super.applyPhysics();
        cooldown--;
        if(cooldown<=0){
            cooldown = 30;
            ammo--;
            //shoot ghost
            if(ammo<=0){
                die();
            }
        }
    }
    public void die(){
        super.die();
        getWorld().removeObject(this);
    }
}
