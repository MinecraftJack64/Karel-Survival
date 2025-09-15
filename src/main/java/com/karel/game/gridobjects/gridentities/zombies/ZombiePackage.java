package com.karel.game.gridobjects.gridentities.zombies;

import com.karel.game.GridObject;

/**
 * Write a description of class ZombiePackage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class ZombiePackage extends GridObject
{
    GridObject source;
    public ZombiePackage(GridObject source){
        this.source = source;
    }
    public GridObject getSource(){
        return source;
    }
    public void setSource(GridObject s){
        source = s;
    }
    public void render(){}
}