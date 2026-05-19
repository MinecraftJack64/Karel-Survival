package com.karel.game.gridobjects.gridentities;

import com.karel.game.GridEntity;

public class DamageIndicator extends GridEntity{
    public DamageIndicator(){
        startHealth(1000, false);
        setTeam("punchingbag");
        setImage("whitesquare.png");
        scaleTexture(10);
    }
    public void behave(){
        super.behave();
        int g = (int)(getHealth()/1000.0*255);
        setTint(g, g, g);
    }
    public boolean canBePulled(){
        return false;
    }
    public String getEntityID(){
        return "damageindicator";
    }
}
