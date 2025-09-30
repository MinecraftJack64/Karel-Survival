package com.karel.game.weapons.waterballoons;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.FlyingProjectile;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class BigWaterBalloon extends FlyingProjectile
{
    public BigWaterBalloon(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setRange(50);
        setDamage(400);
    }
    public double getGravity(){
        return 2;
    }
    public void die(){
        for(int i = 0; i <= 360; i+=72){
            WaterBalloon wb = new WaterBalloon(i+getDirection(), this, true);
            addObjectHere(wb);
        }
        addObjectHere(new WaterPuddle(150, this));
        super.die();
    }
    public boolean covertDamage(){
        return true;
    }
}
