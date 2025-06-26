package com.karel.game.weapons.slicer;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;

import java.util.HashSet;

import com.karel.game.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class BouncingCircSaw extends Bullet
{
    private HashSet<GridEntity> targets = new HashSet<>();
    private int bounces = 10;
    private int notifiables = 0;

    public BouncingCircSaw(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/slicer/projUlt.png");
        scaleTexture(50);
        setSpeed(22);
        setLife(30);
        setDamage(250);
        setNumTargets(-1);
    }
    public void animate(){
        setRealRotation(getRealRotation()+30);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        notifiables++;
        if(notifiables==1)targets.add(targ);
    }
    public void expire(){
        if(bounces<=0||notifiables==0){
            super.expire();
            return;
        }
        bounces--;
        notifiables = 0;
        //point to next target
        GridEntity next = null;
        for(GridEntity g: getWorld().allEntities()){
            if(!targets.contains(g)&&isAggroTowards(g)&&(next==null||distanceTo(g)<distanceTo(next))){
                next = g;
            }
        }
        if(next==null){
            super.expire();
        }else{
            double monangle = face(next, false);
            setDirection(monangle);
            setLife(30);
        }
    }
    public boolean covertDamage(){
        return notifiables>0;
    }
    public double damageSecrecy(){
        return super.damageSecrecy()*0.3;
    }
}
