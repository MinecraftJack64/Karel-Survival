package com.karel.game;

import com.karel.game.weapons.ShieldID;

/**
 * Write a description of class ImmunityShield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ExternalImmunityShield extends Shield
{
    private int duration;
    public ExternalImmunityShield(ShieldID myG, int health){
        super(myG);
        this.duration = health;
    }
    public int processDamage(int dmg, GridObject source){
        //source.notifyDamage(getHolder(), dmg);
        return source==getHolder()?dmg:0;//does not stop damage if source is self
    }
    public void tick(){
        duration--;
        if(duration==0){
            remove();
        }
    }
    public boolean damage(int amt, GridObject source){
        if(duration>=0){
            duration-=amt/3;
            if(duration<=0){
                remove();
            }
        }
        return false;
    }
    public boolean canBreak(){
        return duration>=0;
    }
    public boolean canPierce(){
        return false;
    }
}


