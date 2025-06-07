package com.karel.game.weapons.slicer;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class BouncingCircSaw extends FlyingCircSaw
{
    private int bounces = 5;
    private int notifiables = 5;

    public BouncingCircSaw(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/slicer/proj.png");
        setNumTargets(-1);
    }
    public void animate(){
        setRealRotation(getRealRotation()+30);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        notifiables--;
    }
    public void expire(){
        if(bounces<=0){
            super.expire();
            return;
        }
        bounces--;
        //point to next target
        GridEntity next = null;
        for(GridEntity g: getWorld().allEntities()){
            if(!getHitStory().contains(g)&&isAggroTowards(g)&&(next==null||distanceTo(g)<distanceTo(next))){
                next = g;
            }
        }
        if(next==null){
            super.expire();
        }else{
            double monangle = face(next, false);
            setDirection(monangle);
            setLife(15);
        }
    }
    public boolean covertDamage(){
        return notifiables>0;
    }
    public double damageSecrecy(){
        return super.damageSecrecy()*0.5;
    }
}
