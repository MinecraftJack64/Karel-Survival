package com.karel.game;
/**
 * Write a description of class InfectionEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InfectionEffect extends PoisonEffect//Can also run lambdas
{
    public InfectionEffect(int damage, int interval, GridObject source){
        super(damage, interval, 1, source);
    }
    public void damage(GridEntity e){
        super.damage(e);
    }
}
