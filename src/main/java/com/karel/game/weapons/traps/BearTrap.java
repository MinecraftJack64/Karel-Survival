package com.karel.game.weapons.traps;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Pet;
import com.karel.game.Sounds;
import com.karel.game.effects.EffectID;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class BearTrap extends Pet implements ITrap
{
    /** The damage this bullet will deal */
    private static final int damage = 75;
    private static final int range = 50;
    private int resnapCooldown;
    private boolean isset;
    private GridEntity target;
    private int hitcooldown, hitrate = 20, hitcount = 4;
    private int speed;
    private double dir;
    public BearTrap(double rot, GridEntity source)
    {
        super(source);
        setImage("Weapons/traps/projUlt.png");
        scaleTexture(50);
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
        //move(getRotation()-90, 15);
        if(speed>0){
            move(dir, speed);
            speed*=0.9;
            if(speed == 0){
                isset = true;
            }
        }
        if(resnapCooldown>0){
            resnapCooldown--;
            if(resnapCooldown==25){
                Sounds.play("beartrapresnap");
                explodeOn((int)(range*1.5), damage*4);
            }
            if(resnapCooldown==0){isset = true;}
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
            setImage("Weapons/traps/projUlt2.png");
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
    public void resnap(){
        isset = false;
        if(target!=null){
            target.mobilize(new EffectID(this));
            target = null;
        }
        resnapCooldown = 50;
        setImage("Weapons/traps/projUlt.png");
    }
    public int spawnImmunityLength(){
        return 15;
    }
    public void die(GridObject source){
        if(target!=null){
            target.mobilize(new EffectID(this));
            System.out.println(target+" "+this);
        }
        super.die(source);
    }
    public boolean covertDamage(){
        return hitcount>0;
    }
    public String spriteOrigin(){
        return "";
    }
}
