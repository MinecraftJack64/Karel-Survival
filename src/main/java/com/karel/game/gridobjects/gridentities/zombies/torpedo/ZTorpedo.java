package com.karel.game.gridobjects.gridentities.zombies.torpedo;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;
public class ZTorpedo extends Bullet
{
    double vx, vy;
    double tvx, tvy;
    double cdirspeed = 0.18; // how fast it can change speed
    double pspeed = 13; // target speed
    public ZTorpedo(double rotation, GridObject source)
    {
        super(rotation, source);
        setLife(30);
        setDamage(50);
        setSpeed(0);
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
        setLife(30);
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
