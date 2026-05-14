package com.karel.game.physics;
import java.util.function.Consumer;

import com.karel.game.GridEntity;


/**
 * Write a description of class DasherDoer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AccelerationDasherDoer extends DasherDoer
{
    private int accel = 2;
    public AccelerationDasherDoer(double direction, double speed, int time, double radius, Consumer<GridEntity> onExplode, GridEntity subject){
        super(direction, speed, time, radius, onExplode, subject);
    }
    public AccelerationDasherDoer(double direction, double speed, int time, double radius, int damage, GridEntity subject){
        super(direction, speed, time, radius, damage, subject);
    }
    public boolean dash(){
        boolean result = super.dash();
        speed+=accel;
        return result;
    }
}
