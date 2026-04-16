package com.karel.game.weapons.morphingstone;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Morpher extends Bullet
{
    MorphingStone notifier;
    
    public Morpher(double rotation, GridObject source)
    {
        super(rotation, source);
        setLife(50);
        setDamage(300);
        setSpeed(9);
    }
    
    public Morpher(double rotation, GridObject source, MorphingStone thing)
    {
        this(rotation, source);
        notifier = thing;
    }
    public void animate()
    {
        setRotation(getRotation()+15);
    }
    public void doHit(GridEntity targ){
        if(notifier!=null){
            if(!notifier.notifyHypno(targ)){
                super.doHit(targ);
            }
        }
    }
}
