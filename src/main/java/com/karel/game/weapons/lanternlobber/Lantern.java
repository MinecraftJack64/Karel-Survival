package com.karel.game.weapons.lanternlobber;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.FlyingProjectile;
import com.karel.game.particles.Explosion;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Lantern extends FlyingProjectile
{
    public static final int numFirecrackers = 8;
    private int nf;
    private int life = 100;
    public Lantern(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        nf = 0;
        setRange(100);
        setDamage(300);
        setCheckHitMode(2);
        setDieOnHit(false);
    }
    public double getGravity(){
        return 0.5;
    }
    public void update(){
        super.update();
        if(percentDone()>nf*1.0/numFirecrackers){
            nf++;
            Firecracker f = new Firecracker(0, getHeight(), 0, 6.5, this);
            addObjectHere(f);
        }
        life--;
        if(life<=0){
            die();
        }
    }
    public void die(){
        addObjectHere(new Explosion(getRange()/60));
        super.die();
    }
}
