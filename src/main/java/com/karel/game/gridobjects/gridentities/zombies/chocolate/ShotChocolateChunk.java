package com.karel.game.gridobjects.gridentities.zombies.chocolate;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

public class ShotChocolateChunk extends Bullet {
    public ShotChocolateChunk(double rotation, GridObject source)
    {
        super(rotation, source);
        setDamage(200);
        setSpeed(20);
        setLife(10);
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
