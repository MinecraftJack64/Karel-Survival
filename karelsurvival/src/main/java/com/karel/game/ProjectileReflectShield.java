package com.karel.game;
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
            psource.setDirection(psource.getDirection()-180);
            psource.setTeam(getHolder().getTeam());
            psource.setSelfHarm(true);
            if(psource.getNumTargets()!=-1){
                psource.setNumTargets(psource.getNumTargets()+1);
            }
            return 0;
        }else
        return dmg;//does not stop damage if not projectile
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


