package com.karel.game;

import com.karel.game.gridobjects.hitters.FlyingProjectile;

/**
 * A Boomerang that is lobbed first, then returns to its shooter
 * 
 * @author Jack
 */
public class Reelin extends FlyingProjectile implements IBoomerang
{
    private int phase;//0 - lobbing, 1 - returning, 2 - returned
    private double speed;
    private double direction;
    private boolean damageonreturn;
    public Reelin(double rotation, double targetdistance, double height, GridObject source)
    {
        //focus is 0 to 1
        super(rotation, targetdistance, height, source);
        phase = 0;
    }
    public void setSpeed(double speed){
        this.speed = speed;
    }
    public double getSpeed(){
        return speed;
    }
    public void setDirection(double rot){
        direction = rot;
    }
    public double getDirection(){
        return direction;
    }
    public void setDamageOnReturn(boolean v){
        damageonreturn = v;
    }
    public boolean getDamageOnReturn(){
        return damageonreturn;
    }
    public boolean isReturning(){
        return phase==1;
    }
    public void applyPhysics(){
        if(phase==0){
            super.applyPhysics();
        }else{
            if(phase==1){
                doReturn();
            }
        }
    }
    public void doReturn(){
        if(getSource()==null||distanceTo(getSource())<=getSpeed()/2){
            die();
            return;
        }
        setDirection(face(getSource(), false));
        move(getDirection(), getSpeed());
        if(getDamageOnReturn()){
            checkHit();
        }
    }
    public void die(){
        if(phase==0){
            startReturn();
        }else{
            super.die();
            phase = 2;
        }
    }
    public void startReturn(){
        phase = 1;
    }
    public boolean hasReturned(){
        return phase==2;
    }
}
