package com.karel.game.weapons.snailshell;

import com.karel.game.GridObject;
import com.karel.game.Shield;
import com.karel.game.gridobjects.hitters.Bullet;
import com.karel.game.shields.ShieldID;

/**
 * Write a description of class ImmunityShield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SnailShield extends Shield
{
    private int health;
    private double effectiveness;
    public SnailShield(ShieldID myG, double effectiveness, int health){
        super(myG);
        this.health = health;
        this.effectiveness = effectiveness;
    }
    public int processDamage(int dmg, GridObject source){
        if(!source.covertDamage())source.notifyDamage(getHolder(), (int)(source.damageSecrecy()*dmg));
        if(source instanceof Bullet){
            Bullet psource = (Bullet)source;
            redirectProjectile(psource);
            psource.setTeam(getHolder().getTeam());
            psource.setSelfHit(true);
            psource.resetLife();
            if(psource.getNumTargets()!=-1){
                psource.setNumTargets(psource.getNumTargets()+1);
            }
            psource.setPower(psource.getPower()*effectiveness);
        }
        //health-=dmg*effectiveness;
        if(health<=0){
            remove();
        }
        return (int)(dmg*(1-effectiveness));//does not stop damage if not projectile
    }
    public void setEffectiveness(double n){
        effectiveness = n;
    }
    public void redirectProjectile(Bullet psource){
        psource.setDirection(psource.getDirection()-180);
    }
    public boolean damage(int amt, GridObject source){
        return false;
    }
    public boolean canBreak(){
        return health>=0;
    }
    public int getHealth(){
        return health>0?health:1;
    }
}


