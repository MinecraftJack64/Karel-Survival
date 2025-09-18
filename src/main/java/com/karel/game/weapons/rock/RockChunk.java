package com.karel.game.weapons.rock;

public class RockChunk extends com.karel.game.gridobjects.hitters.FlyingProjectile{
    public RockChunk(double rotation, double targetdistance, double height, com.karel.game.GridObject source){
        super(rotation, targetdistance, height, source);
        setDamage(getDamage()/2);
        setRange(getRange()/2);
    }
    @Override
    public boolean covertDamage(){
        return true;
    }
}
