package com.karel.game;

import com.karel.game.gridobjects.hitters.Bullet;
import com.karel.game.weapons.ShieldID;

/**
 * Write a description of class ImmunityShield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ProjectileReflectShield extends Shield
{
    private int duration;
    public ProjectileReflectShield(ShieldID myG, int health){
        super(myG);
        this.duration = health;
    }
    public int processDamage(int dmg, GridObject source){
        if(!source.covertDamage())source.notifyDamage(getHolder(), (int)(source.damageSecrecy()*dmg));
        if(source instanceof Bullet){
            Bullet psource = (Bullet)source;
            redirectProjectile(psource);
            psource.setTeam(getHolder().getTeam());
            psource.setSelfHit(true);
            if(psource.getNumTargets()!=-1){
                psource.setNumTargets(psource.getNumTargets()+1);
            }
            return 0;
        }else
        return dmg;//does not stop damage if not projectile
    }
    public void redirectProjectile(Bullet psource){
        psource.setDirection(psource.getDirection()-180);
    }
    public void tick(){
        duration--;
        if(duration==0){
            remove();
        }
    }
    public boolean damage(int amt, GridObject source){
        return false;
    }
    public boolean canBreak(){
        return duration>=0;
    }
    public int getHealth(){
        return duration>0?duration:1;
    }
}


