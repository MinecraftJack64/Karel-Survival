package com.karel.game;

import com.karel.game.shields.ShieldID;

public class DecayingArmorShield extends ArmorShield
{
    public DecayingArmorShield(ShieldID myG, int health){
        super(myG, health);
    }
    public void tick(){
        super.tick();
        changeHealth(-1);
    }
}


