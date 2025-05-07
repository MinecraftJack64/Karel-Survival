package com.karel.game;
/**
 * Write a description of class ZombiePackage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ZombiePackage extends GridObject
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
}