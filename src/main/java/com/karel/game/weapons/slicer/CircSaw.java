package com.karel.game.weapons.slicer;

import com.karel.game.Boomerang;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class CircSaw extends Boomerang
{
    private int phase;
    private boolean hasreturned;
    
    public CircSaw(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/slicer/proj.png");
        scaleTexture(50);
        setSpeed(18);
        setNumTargets(-1);
        phase = 1;
        hasreturned = false;
        setDamage(150);
        setLife(25);
        setDamageOnReturn(1);
    }
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void applyPhysics()
    {
        if(phase==1){
            super.applyPhysics();
        }else if(phase==3){
            if(getSpeed()<18){
                setSpeed(getSpeed()+6);
            }
            doReturn();
        }else{
            setLife(1);
            setSpeed(getSpeed()-6);
            super.applyPhysics();
            if(getSpeed()==0){
                phase = 3;
                clearHitStory();
            }
        }
    }
    public int getPhase(){
        return phase;
    }
    @Override
    public void animate(){
        setRealRotation(getRealRotation()+30);
    }
    public void dieForReal(){
        hasreturned = true;
        super.dieForReal();
    }
    public boolean hasReturned(){
        return hasreturned;
    }
    public void expire(){
        phase = 2;
    }
    public void returnNow(){
        setSpeed(22);
        phase = 3;
    }
    public void bounceBack(){
        setSpeed(18);
        if(phase==3){
            setDirection(getDirection()+180);
        }
        phase = 2;
    }
    public void doHit(GridEntity targ){
        if(targ.hasShield()&&targ.getShield().canBreak()){
            targ.getShield().damage((int)(getDamage()*3*getPower()), this);
            if(phase==3){
                bounceBack();
            }else{
                returnNow();
            }
        }
        super.doHit(targ);
    }
}
