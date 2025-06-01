package com.karel.game;

import com.karel.game.weapons.EffectID;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class BearTrap extends Pet
{
    /** The damage this bullet will deal */
    private static final int damage = 75;
    private static final int range = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 0;
    private boolean isset;
    private GridEntity target;
    private int hitcooldown, hitrate = 20, hitcount = 4;
    private int speed;
    private double dir;
    public BearTrap(double rot, GridEntity source)
    {
        super(source);
        isset = false;
        startHealth(400);
        hitcooldown = 0;
        setTeam(source.getTeam());
        speed = 22;
        dir = rot;
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void applyPhysics()
    {
        //move(getRealRotation()-90, 15);
        if(speed>0){
            move(dir, speed);
            speed*=0.9;
            if(speed == 0){
                isset = true;
            }
        }
        if(isset){checkAsteroidHit();hit(1, this);}
        else if(target!=null) attack();
    }
    
    /**
     * Check whether we have hit an asteroid.
     */
    private void checkAsteroidHit()
    {
        GridEntity asteroid = getNearestTarget();
        if (asteroid != null&&distanceTo(asteroid)<range&&isAggroTowards(asteroid)&&asteroid.isOnGround()&&asteroid.canMove()){
            snap(asteroid);
        }
    }
    
    public void snap(GridEntity asteroid){
        isset = false;
        target = asteroid;
        Sounds.play("beartrapsnap");
        target.immobilize(new EffectID(this));
        attack();
    }
    private void attack(){
        if(target.isDead()){
            kill(this);
            return;
        }
        if(hitcooldown<=0){
            hitcooldown = hitrate;
            hitcount--;
            //check status of target
            damage(target, damage);
        }
        hitcooldown--;
    }
    public int spawnImmunityLength(){
        return 15;
    }
    public void die(){
        if(!isset&&target!=null){
            target.mobilize(new EffectID(this));
        }
        super.die();
        getWorld().removeObject(this);
    }
    public boolean covertDamage(){
        return hitcount>0;
    }
}
