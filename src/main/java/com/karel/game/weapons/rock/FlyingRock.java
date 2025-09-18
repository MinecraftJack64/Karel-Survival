package com.karel.game.weapons.rock;

public class FlyingRock extends com.karel.game.gridobjects.hitters.FlyingProjectile{
    private boolean gadget;
    private double height;
    public FlyingRock(double rotation, double targetdistance, double height, com.karel.game.GridObject source, boolean gadget){
        super(rotation, targetdistance, height, source);
        this.gadget = gadget;
        this.height = height;
    }
    public void die(){
        if(gadget){
            for(int i = 0; i <= 360; i+=72){
                addObjectHere(new RockChunk(i+getDirection(), height/2, Math.max(height/2, 20), this));
            }
        }
        super.die();
    }
}
