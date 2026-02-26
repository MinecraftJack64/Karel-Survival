package com.karel.game.weapons.electricfists;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ElectricUppercut extends Bullet
{
    public ElectricUppercut(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(1);
        setLife(18);
        setDamage(100);
        setNumTargets(-1);
    }
    public void applyPhysics(){
        super.applyPhysics();
        if(getSpeed()<=31){
            setSpeed(getSpeed()+1);
        }
    }
    public void doHit(GridEntity targ){
        targ.knockBack(getDirection(), 50, 30, this);
        super.doHit(targ);
    }
}
