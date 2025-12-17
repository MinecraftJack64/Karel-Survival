package com.karel.game.weapons.inferno;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Shield;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.shields.ShieldID;

/**
 * Write a description of class ImmunityShield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BurningShield extends Shield
{
    private int duration;
    private double strength;
    private EffectID burn;
    public BurningShield(ShieldID myG, double strength, int health){
        super(myG);
        this.duration = health;
        this.strength = strength;
    }
    public void setHolder(GridEntity g){
        super.setHolder(g);
        burn = new EffectID(getHolder());
    }
    public int processDamage(int dmg, GridObject source){
        GridEntity g = source.getParentAffecter();
        if(g!=null)g.applyEffect(new PoisonEffect((int)(strength*dmg/3), 30, 3, getHolder(), burn));
        return (int)(dmg*(1-strength));//does not stop damage if source is self
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
}


