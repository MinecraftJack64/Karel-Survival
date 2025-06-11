package com.karel.game;

import com.karel.game.weapons.ShieldID;

/**
 * Write a description of class Pet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pet extends GridEntity
{
    GridEntity spawner;
    public Pet(GridEntity spawner){
        this.spawner = spawner;
        if(spawnImmunityLength()>0)applyShield(new ExternalImmunityShield(new ShieldID(this, "spawn immunity"), spawnImmunityLength()));
    }
    public GridEntity getSpawner(){
        return spawner;
    }
    public boolean willNotify(){
        return false;
    }
    public GridEntity getTarget(){
        //return getWorld().player;
        GridEntity candidate = getNearestTarget();
        if(candidate == null){
            candidate = getSpawner();
        }
        return candidate;//use this for now
    }
    public GridEntity getNearestTarget(){
        GridEntity res = null;
        double max = 0;
        for(GridEntity e: getWorld().allEntities){
            if(!isAggroTowards(e)||!e.canDetect()){
                continue;
            }
            if(distanceTo(e)<max||res==null){
                res = e;
                max = distanceTo(e);
            }
        }
        return res;
    }
    public boolean notifySpawnerDamage(){//charge ult of spawner?
        return true;
    }
    public int spawnImmunityLength(){
        return 45;
    }
    public void setImage(String path){
        super.setImage("GridEntities/Pets/"+path);
    }
    public void notifyDamage(GridEntity target, int amt){
        if(notifySpawnerDamage()&&getSpawner()!=null&&!getSpawner().isDead()){
            getSpawner().notifyDamage(target, amt);
        }else{
            super.notifyDamage(target, amt);
        }
    }
}
