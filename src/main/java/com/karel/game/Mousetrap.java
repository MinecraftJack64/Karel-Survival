package com.karel.game;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Mousetrap extends Trap
{
    /** The damage this bullet will deal */
    private int damage = 100;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 150;
    private boolean isset;
    private GridEntity target;
    private int hitcooldown, hitrate = 30, hitcount = 4;
    private int speed;
    private double dir;
    public Mousetrap(GridObject source)
    {
        super(source);
        isset = true;
        hitcooldown = 0;
        setTeam(source.getTeam());
    }
    public Mousetrap(double rot, GridObject source)
    {
        this(source);
        speed = 22;
        dir = rot;
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
            //move(getRotation()-90, 15);
            if(isset){
                if(speed>0){
                    move(dir, speed);
                    speed*=0.9;
                }
                checkAsteroidHit();life--;
            }
            else attack();
        }
    }
    
    /**
     * Check whether we have hit an asteroid.
     */
    private void checkAsteroidHit()
    {
        GridEntity asteroid = getOneCollidingObject();
        if (asteroid != null&&isAggroTowards(asteroid)&&asteroid.isOnGround()){
            //getWorld().removeObject(this);
            isset = false;
            target = asteroid;
            Sounds.play("mousetrapsnap");
            //asteroid.hit(damage, this);
            target.applyEffect(new StunEffect(150, this));
            //target.applyeffect(new SpeedPercentageEffect(5, 300));
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
            target.hit(damage, this);
            damage-=30;
        }
        hitcooldown--;
        if(hitcount<=0){
            die();
        }
    }
    public void die(){
        super.die();
        getWorld().removeObject(this);
    }
}
