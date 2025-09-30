package com.karel.game.gridobjects.collectibles;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * a object that moves towards a certain other object to be collected by them
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class Collectible extends GridObject
{
    private double range = 40;//how far away this collectible has to be to move towards collector
    private double speed = 9;//how fast this collectible will move towards its collector
    private int cooldown; //wait a certain amount of time before being able to be picked up
    private boolean active = true, collected = false;
    private GridObject collector;
    public Collectible(){
        setImage("Objects/beeper0.png");
    }
    public void setRange(double r){
        range = r;
    }
    public double getRange(){
        return range;
    }
    public void setSpeed(double r){
        speed = r;
    }
    public double getSpeed(){
        return speed;
    }
    public GridObject getTarget(){
        GridObject targ = getNearestTarget();
        if(targ==null)return getWorld().getPlayer();
        return targ;
    }
    public boolean isPotentialTarget(GridEntity g){
        return isAlliedWith(g);
    }
    public boolean isActive(){
        return cooldown==0&&active;
    }
    public void setActive(boolean v){
        active = v;
    }
    public void setCooldown(int c){
        cooldown = c;
    }
    public void update()
    {
        //if(!getWorld().gameStatus().equals("running"))return;
        double targang = face(getTarget(), false);
        double monangle = targang;
        if(cooldown>0)cooldown--;
        if(distanceTo(getTarget())<getRange()&&isActive()){
            move(monangle, getSpeed());
            onCollecting();
        }
        super.update();
        if(distanceTo(getTarget())<getSpeed()/2+1&&isActive())collect(getTarget());
    }
    public void collect(GridObject t){
        collected = true;
        collector = t;
        die();
    }
    public void onCollecting(){
        //
    }
    public boolean collected(){
        return collected;
    }
    public GridObject getCollector(){
        return collector;
    }
    public void die(){
        getWorld().removeObject(this);
    }
}
