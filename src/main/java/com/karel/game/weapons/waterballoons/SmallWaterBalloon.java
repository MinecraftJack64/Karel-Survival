package com.karel.game.weapons.waterballoons;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.FlyingProjectile;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SmallWaterBalloon extends FlyingProjectile
{
    public SmallWaterBalloon(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setRange(50);
        setDamage(200);
    }
    public double getGravity(){
        return 2;
    }
    public void die(){
        addObjectHere(new WaterPuddle(150, this));
        super.die();
    }
    public boolean covertDamage(){
        return true;
    }
}
