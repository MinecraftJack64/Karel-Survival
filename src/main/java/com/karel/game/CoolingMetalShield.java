package com.karel.game;

import com.karel.game.shields.ShieldID;

/**
 * Tanks a certain amount of hits. A single 1 damage hit does the same damage to this shield as a 2000 damage hit
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CoolingMetalShield extends MetalShield
{
    private int cooldownMax, cooldown;
    public CoolingMetalShield(ShieldID myG, int health, int cooldown){
        super(myG, health);
        this.cooldownMax = cooldown;
    }
    public int processDamage(int dmg, GridObject source){
        if(cooldown>0)return 0;
        cooldown = cooldownMax;
        int a = 0;
        for(int i = 0; i < damageMultiplier(); i++){
            a = super.processDamage(dmg, source);
        }
        return a;
    }
    public int damageMultiplier(){
        return 1;
    }
    public void tick(){
        super.tick();
        cooldown--;
    }
}


