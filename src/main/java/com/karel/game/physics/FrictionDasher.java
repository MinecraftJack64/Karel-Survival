package com.karel.game.physics;

import com.karel.game.GridEntity;

/**
 * Write a description of class Dasher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FrictionDasher extends Dasher
{
    public FrictionDasher(double direction, double speed, int time, GridEntity subject){
        super(direction, speed, time, subject);
    }
    public boolean dash(){//return false if dashing is done
        boolean r = super.dash();
        speed--;
        return r;
    }
}
