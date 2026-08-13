package com.karel.game.gridobjects.gridentities.zombies.supernova;
import java.util.HashSet;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.StunEffect;
import com.karel.game.effects.VisionPercentageEffect;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZSupernovaMatter extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public ZSupernovaMatter(double rotation, GridObject source)
    {
        super(rotation, new HashSet<GridEntity>(), source);
    }
    public ZSupernovaMatter(double rotation, HashSet<GridEntity> h, GridObject source)
    {
        super(rotation, h, source);
        setSpeed(20);
        setLife(11);
        setDamage(100);
        setNumTargets(2);
    }
    public void doHit(GridEntity t){
        t.applyEffect(new StunEffect(15, this));
        t.applyEffect(new VisionPercentageEffect(0.3, 15, this));
        super.doHit(t);
    }
}
