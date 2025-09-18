package com.karel.game.weapons.rock;
import java.util.HashSet;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * An straight projectile long range asteroid that splits into smaller asteroids upon impact.
 * 
 * @author Poul Henriksen
 */
public class Asteroid extends Bullet
{
    private int size;
    private boolean upgrade;

    public Asteroid(double rotation, GridObject source, boolean upgrade)
    {
        this(rotation, 3, new HashSet<GridEntity>(), source, upgrade);
    }
    public Asteroid(double rotation, int size, HashSet<GridEntity> h, GridObject source, boolean upgrade)
    {
        super(rotation, h, source);
        this.upgrade = upgrade;
        scaleTexture(5+size*15, 5+size*15);
        setTexture("rock.png");
        setLife(200);
        setMultiHit(false);
        //size 3, 2, 1
        setDamage(size*70);
        setSpeed(3+5*size);//18, 13, 8
        this.size = size;
    }
    public void applyPhysics(){
        if(upgrade){
            //Home in on nearest target
            GridEntity target = getNearestTarget();
            if(target != null){
                double dirToTarget = face(target, false)-getDirection();
                if(Math.abs(dirToTarget)<70&&distanceTo(target)<600){
                    double amt = dirToTarget*((600-distanceTo(target))/2000);
                    setDirection(getDirection()+Math.min(amt, 1.5));
                }
            }
        }
        super.applyPhysics();
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(size<=1){
            return;
        }
        Asteroid l = new Asteroid(getDirection()-35, size-1, getHitStory(), getSource(), upgrade);
        Asteroid r = new Asteroid(getDirection()+35, size-1, getHitStory(), getSource(), upgrade);
        getWorld().addObject(r, getX(), getY());
        getWorld().addObject(l, getX(), getY());
    }
    public double damageSecrecy(){
        return super.damageSecrecy()*0.5;
    }
}