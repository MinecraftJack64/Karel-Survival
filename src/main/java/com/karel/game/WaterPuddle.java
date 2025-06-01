package com.karel.game;
import java.util.HashMap;

import com.karel.game.weapons.EffectID;
/**
 * Write a description of class WaterPuddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaterPuddle extends Puddle
{
    private HashMap<GridEntity, Integer> lastAffected;
    private EffectID stun;
    public WaterPuddle(int duration, GridObject source){
        // 75 frames of 0.5 slow at 0.5 focus, 15 frames of no slow at 2 focus
        super(100, 1, duration, source);
        lastAffected = new HashMap<GridEntity, Integer>();
        stun = new EffectID(this);
    }
    public void doHit(GridEntity t){
        if(!lastAffected.containsKey(t)){
            lastAffected.put(t, 1);
        }else{
            lastAffected.put(t, lastAffected.get(t)+2);
            if(lastAffected.get(t)>=60){
                t.applyEffect(new StunEffect(10, this, stun));
                lastAffected.put(t, 1);
            }
        }
    }
    public void checkHit(){
        super.checkHit();
        for(GridEntity g: lastAffected.keySet()){
            if(!getHitStory().contains(g)){
                lastAffected.put(g, lastAffected.get(g)-1);
            }
            if(lastAffected.get(g)<=0){
                lastAffected.remove(g);
            }
        }
    }
}
