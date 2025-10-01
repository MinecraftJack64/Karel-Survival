package com.karel.game.weapons.grenade;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.StunEffect;
import com.karel.game.gridobjects.hitters.FlyingProjectile;
import com.karel.game.particles.Explosion;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Flashbang extends FlyingProjectile
{
    public Flashbang(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setRange(120);
        setDamage(0);
    }
    public void doHit(GridEntity g){
        g.applyEffect(new StunEffect(g.isFacing(this)?60:45, this));
        super.doHit(g);
    }
    public void die(){
        addObjectHere(new Explosion(getRange()/60));
        super.die();
    }
    public double getGravity(){
        return 2;
    }
}
