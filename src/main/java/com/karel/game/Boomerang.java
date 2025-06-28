package com.karel.game;
/**
 * Write a description of class Boomerang here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Boomerang extends Bullet implements IBoomerang
{
    private int phase = 0;//0 - regular, 1 - return, 2 - other
    private int returnOnExpire;//0 - on die, 1 - on expire, 2 - on finish
    private double returnSpeed;
    private int returnDamage;
    private boolean hasreturned;
    private int damageonreturn = 0;//0 - on phase 0 only, 1 - both ways, 2 - only when returning
    public Boomerang(double dir, GridObject source){
        super(dir, source);
        setExpireReturn(0);
        hasreturned = false;
    }
    public void setExpireReturn(int s){
        returnOnExpire = s;
    }
    public void setReturnSpeed(double s){returnSpeed = s;}
    public double getReturnSpeed(){return returnSpeed;}
    public void setDamageOnReturn(int v){damageonreturn = v;}
    public void setReturnDamage(int d){returnDamage = d;}
    public int getReturnDamage(){return returnDamage;}
    public void applyPhysics(){
        if(phase==0){
            super.applyPhysics(damageonreturn!=2);
        }else{
            doReturn();
        }
    }
    public void doReturn(){
        if(getSource()==null||distanceTo(getSource())<=getSpeed()/2){
            dieForReal();
            return;
        }
        if(damageonreturn>0){
            setNumTargets(-1);
            checkHit();
        }
        setDirection(face(getSource(), false));
        move(getDirection(), getSpeed());
    }
    public void animate(){
        super.animate();
        setRotation(getDirection()+90);
    }
    public void expire(){
        if(returnOnExpire==1)startReturn();
        else die();
    }
    public void die(){
        if(returnOnExpire==0)startReturn();
        else dieForReal();
    }
    public void dieForReal(){
        super.die();
        hasreturned = true;
    }
    public void finish(){
        if(returnOnExpire==2)startReturn();
        else die();
    }
    public void startReturn(){
        phase = 1;
        if(getReturnSpeed()>0)setSpeed(getReturnSpeed());
        if(damageonreturn>0){
            if(getReturnDamage()==0){
                setReturnDamage(getDamage());
            }
            setDamage(getReturnDamage());
            getHitStory().clear();
        }
    }
    public boolean hasReturned(){
        return hasreturned;
    }
}
