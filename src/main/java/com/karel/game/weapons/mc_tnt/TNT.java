package com.karel.game.weapons.mc_tnt;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.FlyingProjectile;
import com.karel.game.particles.Explosion;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class TNT extends FlyingProjectile
{
    private int life = 45;
    private boolean lit = false;
    private boolean isSuper;
    public TNT(double rotation, double targetdistance, GridObject source, boolean issuper)
    {
        super(rotation, targetdistance, 50, source);
        setRange(100);
        setDamage(500);
        setCheckHitMode(2);
        setDieOnHit(false);
        isSuper = issuper;
    }
    public double getGravity(){
        return 2;
    }
    public void update(){
        super.update();
        if(lit)life--;
        if(life<=0){
            die();
        }
    }
    public void light(){
        lit = true;
    }
    public void die(){
        addObjectHere(new Explosion(getRange()/60));
        if(isSuper){
            knockBackOn(100, 100);
        }else knockBackOnEnemies(100, 100);
        super.die();
    }
}
