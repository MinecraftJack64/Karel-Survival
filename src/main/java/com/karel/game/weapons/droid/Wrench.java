package com.karel.game.weapons.droid;

public class Wrench extends com.karel.game.gridobjects.hitters.FlyingProjectile{
    private boolean targetSet;
    public Wrench(double rotation, double targetdistance, double height, com.karel.game.GridObject source, boolean targetSet){
        super(rotation, targetdistance, height, source);
        this.targetSet = targetSet;
        setDamage(150);
        setRange(35);
    }
    public void die(){
        if(targetSet){
        }
        super.die();
    }
}
