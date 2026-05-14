package com.karel.game.weapons.cab;

import com.karel.game.GridEntity;
import com.karel.game.effects.EffectID;
import com.karel.game.physics.DasherDoer;


/**
 * Write a description of class DasherDoer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CarryDasher extends DasherDoer
{
    private EffectID stun = new EffectID("carrydashstun");
    public CarryDasher(double direction, double speed, int time, double radius, int damage, GridEntity subject){
        super(direction, speed, time, radius, null, subject);
        onExplode = (g)->{
            subject.damage(g, damage);
            g.stun(stun);
        };
    }
    public boolean dash(){
        boolean result = super.dash();
        for(GridEntity g: hitstory){
            g.pull(direction, speed);
        }
        if(!result){
            for(GridEntity g: hitstory){
                g.unstun(stun);
            }
        }
        return result;
    }
    public void interrupt(){
        for(GridEntity g: hitstory){
            g.unstun(stun);
        }
        hitstory.clear();
    }
}
