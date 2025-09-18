package com.karel.game.gridobjects.gridentities.zombies.breadboxer;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class PunchMissile extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    double vx, vy;
    double tvx, tvy;
    double cdirspeed = 2;
    double pspeed = 17;
    public PunchMissile(double rotation, GridObject source)
    {
        super(rotation, source);
        setLife(10);
        setDamage(75);
        setSpeed(0);
        setImage("Projectiles/Bullets/zglove.png");
        setRotation(getRotation()-90);
        tvx = Math.cos((rotation-90)*Math.PI/180)*pspeed;
        tvy = Math.sin((rotation-90)*Math.PI/180)*pspeed;
        vx = tvx;
        vy = tvy;
    }
    public void setRotation(double rot){
        super.setRotation(rot+90);
    }
    public void applyPhysics(){
        super.applyPhysics();
        GridEntity g = getTarget();
        double dir = face(g==null?getWorld().getPlayer():g, true)-90;
        tvx = Math.cos(dir*Math.PI/180)*pspeed;
        tvy = Math.sin(dir*Math.PI/180)*pspeed;
        if(vx>tvx+cdirspeed/2){
            vx-=cdirspeed;
        }else if(vx<tvx-cdirspeed/2){
            vx+=cdirspeed;
        }
        if(vy>tvy+cdirspeed/2){
            vy-=cdirspeed;
        }else if(vy<tvy-cdirspeed/2){
            vy+=cdirspeed;
        }
        translate(vx, vy);
    }
    public GridEntity getTarget(){
        GridEntity next = null;
        for(GridEntity g: getWorld().allEntities()){
            if(!getHitStory().contains(g)&&isAggroTowards(g)&&(next==null||distanceTo(g)<distanceTo(next))){
                next = g;
            }
        }
        return next;
    }
}
