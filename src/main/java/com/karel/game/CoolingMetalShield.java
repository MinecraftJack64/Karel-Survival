package com.karel.game;

import com.karel.game.weapons.ShieldID;

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
        return super.processDamage(dmg, source);
    }
    public void tick(){
        super.tick();
        cooldown--;
    }
}


