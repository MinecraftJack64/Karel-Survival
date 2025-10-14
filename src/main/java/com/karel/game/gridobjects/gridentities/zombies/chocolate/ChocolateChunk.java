package com.karel.game.gridobjects.gridentities.zombies.chocolate;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.FlyingProjectile;

public class ChocolateChunk extends FlyingProjectile {
    public ChocolateChunk(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setRange(50);
        setDamage(200);
    }
    public double getGravity(){
        return 1;
    }
    public void die(){
        for(int i = 0; i <= 360; i+=45){
            ChocolateFragment wb = new ChocolateFragment(i+getDirection(), this);
            addObjectHere(wb);
        }
        addObjectHere(new ChocolatePuddle(false, this));
        super.die();
    }
}
