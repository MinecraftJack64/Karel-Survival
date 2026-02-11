package com.karel.game.gridobjects.gridentities;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.ui.text.Counter;

class DamagePoint{
    int time;
    int damage;
    DamagePoint(int t, int d){
        time = t;
        damage = d;
    }
}

public class PunchingBag extends GridEntity{
    // TODO: consider changing system to use total over all time, but reset after inactivity
    private Queue<DamagePoint> damages;
    private Counter display;
    private int now;
    private int totalDamage = 0;
    private int frame = 150;
    private int resetDelay = 150;
    public PunchingBag(){
        startHealth(300);
        setTeam("punchingbag");
        damages = new ConcurrentLinkedQueue<>();
        display = new Counter("DPS: ", 20);
        setImage("karelnOff.png");
        scaleTexture(60);
    }
    public void behave(){
        super.behave();
        now++;
        while(!damages.isEmpty()&&damages.peek().time<now-frame){
            totalDamage-=damages.remove().damage;
            display.setValue(totalDamage/(frame/30));
        }
        if(resetDelay>0)resetDelay--;
        else hit(1, this);
    }
    public void hitIgnoreShield(int dmg, double exp, GridObject source){
        super.hitIgnoreShield(source==this?dmg:0, exp, source);
        if(source!=this){
            damages.add(new DamagePoint(now, dmg));
            totalDamage+=dmg;
            display.setValue(totalDamage/(frame/30));
            heal(this, getMaxHealth());
            resetDelay = 150;
        }
    }
    public void notifyWorldAdd(){
        super.notifyWorldAdd();
        getWorld().addObject(display, getX(), getY());
    }
    public void notifyWorldRemove(){
        getWorld().removeObject(display);
        super.notifyWorldRemove();
    }
    public boolean canBePulled(){
        return false;
    }
    public String getEntityID(){
        return "punchingbag";
    }
}
