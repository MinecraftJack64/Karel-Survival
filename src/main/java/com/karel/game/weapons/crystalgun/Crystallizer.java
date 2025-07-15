package com.karel.game.weapons.crystalgun;

import com.karel.game.Bullet;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Crystallizer extends Bullet
{
    private boolean isSuper;
    public Crystallizer(double rotation, GridObject source, boolean isSuper)
    {
        super(rotation, source);
        this.isSuper = isSuper;
        setSpeed(10);
        setLife(35);
        setDamage(0);
        setNumTargets(3);
    }
    public void doHit(GridEntity targ){
        Crystal c = isSuper?new ColdCrystal(targ, this):new Crystal(targ, this);
        targ.addObjectHere(c);
    }
}
