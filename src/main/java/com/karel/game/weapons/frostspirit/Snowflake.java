package com.karel.game.weapons.frostspirit;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

public class Snowflake extends Bullet {
    public Snowflake(double rot, GridObject source){
        super(rot, source);
        setLife(9);
        setSpeed(18);
        setNumTargets(-1);
        setDamage(50);
    }
    public void applyPhysics(){
        super.applyPhysics();
        setDirection(getDirection()+10);
    }
}
