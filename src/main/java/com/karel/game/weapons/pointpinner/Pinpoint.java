package com.karel.game.weapons.pointpinner;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.EffectID;
import com.karel.game.gridobjects.hitters.FlyingProjectile;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Pinpoint extends FlyingProjectile
{
    GridEntity target;
    double tx, ty;
    EffectID immobilize, weaken;
    public Pinpoint(boolean issuper, GridObject source)
    {
        super(0, 0, 80, source);
        setRange(35);
        setNumTargets(1);
        setDieOnHit(false);
        setImage("anchor.png");
        immobilize = new EffectID(this);
        if(issuper){
            weaken = new EffectID(this);
        }
        //if(load instanceof GridEntity)getWorld().allEntities.add((GridEntity)load);
    }
    public double getGravity(){
        return 2;
    }
    public void doHit(GridEntity g){
        damage(g, 150);
        g.ground();
        g.setSpeedMultiplier(0, immobilize);
        if(weaken!=null){
            g.setPower(0.5, weaken);
        }
        target = g;
        tx = target.getX();
        ty = target.getY();
    }
    public void update(){
        if(target!=null){
            if(target.isDead())die();
            else{
                target.ground();
                target.setSpeedMultiplier(0, immobilize);
                if(distanceTo(target)<50){
                    target.pullTo(tx, ty, this);
                }else{
                    target.unground();
                    target.setSpeedMultiplier(1, immobilize);
                    die();
                }
            }
        }else{
            super.update();
        }
    }
    public void applyPhysics(){
        if(percentDone()<=0.5){setHeight(80);continueFrame();}
        else super.applyPhysics();
        if(getHeight()==0&&target==null){//didn't hit anything
            die();
        }
    }
}
