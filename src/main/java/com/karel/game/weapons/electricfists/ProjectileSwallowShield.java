package com.karel.game.weapons.electricfists;

import java.util.ArrayList;

import com.karel.game.ProjectileReflectShield;
import com.karel.game.gridobjects.hitters.Bullet;
import com.karel.game.shields.ShieldID;

/**
 * Write a description of class ImmunityShield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ProjectileSwallowShield extends ProjectileReflectShield
{
    ArrayList<ArrayList<Bullet>> record;
    public ProjectileSwallowShield(ShieldID myG, int health){
        super(myG, health);
        record = new ArrayList<ArrayList<Bullet>>();
        for(int i = 0; i < health; i++){
            record.add(new ArrayList<>());
        }
    }
    public void redirectProjectile(Bullet psource){
        record.get(getHealth()-1).add(psource);
        getHolder().getWorld().removeObject(psource);
    }
    public ArrayList<ArrayList<Bullet>> getRecord(){
        return record;
    }
    public boolean canBreak(){
        return false;
    }
}


