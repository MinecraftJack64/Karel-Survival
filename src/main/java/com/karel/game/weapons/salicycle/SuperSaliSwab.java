package com.karel.game.weapons.salicycle;

import com.karel.game.CoolingMetalShield;
import com.karel.game.GridEntity;
import com.karel.game.weapons.ShieldID;

public class SuperSaliSwab extends SaliSwab{
    public SuperSaliSwab(double direction, GridEntity g){
        super(direction, g);
        startHealthShield(new ScabShield(new ShieldID(this), 8, 10));
    }
    public class ScabShield extends CoolingMetalShield{
        public ScabShield(ShieldID myG, int health, int cooldown){
            super(myG, health, cooldown);
        }
        public int damageMultiplier(){
            return isAttacking()?2:1;
        }
    }
    public String getPetID(){
        return "salicycle-swab+";
    }
}
