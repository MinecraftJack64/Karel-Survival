package com.karel.game.weapons.morphingstone;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.KnockbackEffect;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Roar extends Bullet
{
    
    public Roar(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/scream/projUlt.png");
        scaleTexture(125);
        setRotation(rotation);
        setSpeed(20);
        setLife(30);
        setDamage(100);
        setNumTargets(-1);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        targ.applyEffect(new KnockbackEffect(face(getSource(), false), distanceTo(getSource()), 150, this));
    }
}
