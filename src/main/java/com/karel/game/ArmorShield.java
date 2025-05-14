package com.karel.game;
/**
 * Write a description of class ArmorShield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ArmorShield extends Shield
{
    private int health, maxhealth;
    public ArmorShield(ShieldID myG, int health){
        super(myG);
        this.health = this.maxhealth = health;
    }
    public int processDamage(int dmg, GridObject source){
        health-=dmg;
        if(!source.covertDamage())source.notifyDamage(getHolder(), (int)(source.damageSecrecy()*(dmg+(health<0?health:0))));
        if(health<=0){
            remove();
            Sounds.play("armorshieldbreak");
        }
        return 0;
    }
    public boolean damage(int amt, GridObject source){
        processDamage(amt, source);
        return false;
    }
    protected void changeHealth(int amt){
        health+=amt;
        if(health<=0){
            remove();
        }else if(health>maxhealth){
            health = maxhealth;
        }
    }
    public boolean canBreak(){
        return true;
    }
    public int getHealth(){
        return health;
    }
}


