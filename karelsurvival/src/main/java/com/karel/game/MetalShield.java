package com.karel.game;
/**
 * Tanks a certain amount of hits. A single 1 damage hit does the same damage to this shield as a 2000 damage hit
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MetalShield extends Shield
{
    private int health, maxhealth;
    public MetalShield(ShieldID myG, int health){
        super(myG);
        this.health = this.maxhealth = health;
    }
    public int processDamage(int dmg, GridObject source){
        if(dmg<=0)return 0;
        health--;
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


