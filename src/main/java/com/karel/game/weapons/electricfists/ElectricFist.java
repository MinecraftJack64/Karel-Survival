package com.karel.game.weapons.electricfists;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ElectricFist extends Bullet
{
    double vx, vy;
    double tvx, tvy;
    double cdirspeed = 2;
    double pspeed = 17;
    private double tx, ty;
    public ElectricFist(double rotation, double xt, double yt, GridObject source)
    {
        super(rotation, source);
        setLife(10);
        setDamage(150);
        setSpeed(0);
        setImage("Projectiles/Bullets/zglove.png");
        tvx = Math.cos((rotation-90)*Math.PI/180)*pspeed;
        tvy = Math.sin((rotation-90)*Math.PI/180)*pspeed;
        vx = tvx;
        vy = tvy;
        tx = xt;
        ty = yt;
    }
    public void applyPhysics(){
        super.applyPhysics();
        double dir = face(tx, ty, true)-90;
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
    public void doHit(GridEntity g){
        super.doHit(g);
        if(g.isDead()){
            setNumTargets(getNumTargets()+1);
        }
    }
}
