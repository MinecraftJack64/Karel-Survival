package com.karel.game.weapons.punchcards;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class PunchCard extends Bullet
{
    public PunchCard(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(15);
        setLife(18);
        setDamage(50);
    }
    public void doHit(GridEntity targ){
        targ.knockBack(getDirection(), 30, 10, this);
        super.doHit(targ);
    }
}
