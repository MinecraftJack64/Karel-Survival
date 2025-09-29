package com.karel.game.gridobjects.gridentities.zombies.gatherer;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class BeeperShot extends Bullet
{
    private GridEntity target;
    
    public BeeperShot(GridEntity target, GridObject source)
    {
        super(0, source);
        this.target = target;
        setDirection(source.face(target, false));
        setImage("Objects/beeper1.png");
        setSpeed(20);
        setLife((int)(source.distanceTo(target)/getSpeed()));
        setAggression(false);
    }
    public void die(){
        addObjectHere(new Zombeeper(target));
        super.die();
    }
}
