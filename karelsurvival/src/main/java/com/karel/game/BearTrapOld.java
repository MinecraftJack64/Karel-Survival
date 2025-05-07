package com.karel.game;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class BearTrapOld extends Trap
{
    /** The damage this bullet will deal */
    private static final int damage = 75;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 2000;
    private boolean isset;
    private GridEntity target;
    private int hitcooldown, hitrate = 20, hitcount = 4;
    
    public BearTrapOld(GridObject source)
    {
        super(source);
        isset = true;
        hitcooldown = 0;
        setTeam(source.getTeam());
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void applyPhysics()
    {
        if(life <= 0) {
            die();
        } 
        else {
            //move(getRealRotation()-90, 15);
            if(isset){checkAsteroidHit();life--;}
            else attack();
        }
    }
    
    /**
     * Check whether we have hit an asteroid.
     */
    private void checkAsteroidHit()
    {
        GridEntity asteroid = (GridEntity) getOneIntersectingObject(GridEntity.class);
        if (asteroid != null&&isAggroTowards(asteroid)&&asteroid.isOnGround()&&asteroid.canMove()){
            isset = false;
            target = asteroid;
            Sounds.play("mousetrapsnap");
            target.stun(new EffectID(this));
            attack();
        }
    }
    private void attack(){
        if(target.isDead()){
            die();
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
    public void die(){
        if(!isset){
            target.unstun(new EffectID(this));
        }
        super.die();
        getWorld().removeObject(this);
    }
    public boolean covertDamage(){
        return hitcount>0;
    }
}
