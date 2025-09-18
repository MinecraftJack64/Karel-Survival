package com.karel.game;

import com.karel.game.gridobjects.hitters.Bullet;
import com.karel.game.weapons.ShieldID;

/**
 * Write a description of class ImmunityShield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ProjectileParryShield extends ProjectileReflectShield
{
    public ProjectileParryShield(ShieldID myG, int health){
        super(myG, health);
    }
    public void redirectProjectile(Bullet psource){
        if(getHolder().getTarget() == null) {
            super.redirectProjectile(psource);
        }else psource.setDirection(getHolder().face(getHolder().getTarget(), false));
    }
}


