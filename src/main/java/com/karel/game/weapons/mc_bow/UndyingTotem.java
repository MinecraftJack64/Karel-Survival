package com.karel.game.weapons.mc_bow;

import com.karel.game.GridObject;
import com.karel.game.Shield;
import com.karel.game.effects.HealEffect;
import com.karel.game.shields.ShieldID;

public class UndyingTotem extends Shield{
    private int duration;
    private boolean isSuper;
    public UndyingTotem(ShieldID myG, int health, boolean issuper){
        super(myG);
        this.duration = health;
        isSuper = issuper;
    }
    public int processDamage(int dmg, GridObject source){
        if(getHolder().getHealth()-dmg<=0){
            getHolder().damage(getHolder(), getHolder().getHealth()-1);
            getHolder().heal(getHolder(), (int)(getHolder().getMaxHealth()*0.25)-1);
            if(isSuper){
                getHolder().applyEffect(new HealEffect((int)(getHolder().getMaxHealth()/16.0), 30, 4, getHolder()));
            }
            remove();
            return 0;
        }
        return dmg;
    }
    public void tick(){
        duration--;
        if(duration==0){
            remove();
        }
    }
    public int getDuration(){
        return duration;
    }
    public int getHealth(){
        return duration>0?duration:1;
    }
    public boolean canBreak(){
        return duration>=0;
    }
}
