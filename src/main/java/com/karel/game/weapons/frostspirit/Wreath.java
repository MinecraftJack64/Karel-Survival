package com.karel.game.weapons.frostspirit;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.FlyingProjectile;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Wreath extends FlyingProjectile
{
    public Wreath(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setRange(50);
        setDamage(150);
    }
    public double getGravity(){
        return 2;
    }
    public void die(){
        for(int i = 0; i <= 360; i+=90){
            MiniWreath wb = new MiniWreath(i+45+getDirection(), this, true);
            addObjectHere(wb);
        }
        super.die();
    }
}
