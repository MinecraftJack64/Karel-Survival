package com.karel.game.weapons.droid;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A heal charge homing bullet
 * 
 * @author Poul Henriksen
 */
public class DroidNanobot extends Bullet
{
    double vx, vy;
    double tvx, tvy;
    double cdirspeed = 2;
    double pspeed = 17;
    private GridEntity targ;
    public DroidNanobot(double rotation, GridEntity g, GridObject source)
    {
        super(rotation, source);
        setLife(2);
        setDamage(0);
        setHitAllies(true);
        setAggression(false);
        setSpeed(0);
        setImage("Projectiles/Bullets/zglove.png");
        tvx = Math.cos((rotation-90)*Math.PI/180)*pspeed;
        tvy = Math.sin((rotation-90)*Math.PI/180)*pspeed;
        vx = tvx;
        vy = tvy;
        targ = g;
    }
    public void applyPhysics(){
        super.applyPhysics();
        setLife(2);
        double dir = face(targ.getX(), targ.getY(), true)-90;
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
        if(targ.isDead()||!targ.isInWorld()){
            die();
        }
    }
    public void doHit(GridEntity g){
        heal(g, 50);
        super.doHit(g);
    }
}
