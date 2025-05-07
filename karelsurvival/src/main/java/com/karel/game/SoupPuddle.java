package com.karel.game;
import java.util.HashSet;
/**
 * Write a description of class SoupPuddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SoupPuddle extends Puddle
{
    private HashSet<GridEntity> lastAffected;
    private EffectID slow;
    private double focus;
    public SoupPuddle(double fc, GridObject source){
        // 75 frames of 0.5 slow at 0.5 focus, 15 frames of no slow at 2 focus
        super(100, 1, 95-(int)fc*40, source);
        setTrackAfterHit(true);
        lastAffected = new HashSet<GridEntity>();
        slow = new EffectID(this);
        focus = fc;
    }
    public void doHit(GridEntity t){
        /*if(!getHitStory().contains(t))*/t.setSpeedMultiplier(0.5+(focus-0.5)/3, slow);
    }
    public void afterHit(GridEntity t){
        System.out.println("d");
        t.setSpeedMultiplier(1, slow);
    }
}
