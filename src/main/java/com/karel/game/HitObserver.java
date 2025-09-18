package com.karel.game;

import com.karel.game.gridobjects.hitters.Hitter;

/**
 * Write a description of class HitObserver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface HitObserver  
{
    public void notifyHit(Hitter me, GridEntity ge);
}
