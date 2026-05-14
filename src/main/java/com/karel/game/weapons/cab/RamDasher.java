package com.karel.game.weapons.cab;
import java.util.function.Consumer;

import com.karel.game.GridEntity;
import com.karel.game.physics.AccelerationDasherDoer;


/**
 * Write a description of class DasherDoer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RamDasher extends AccelerationDasherDoer
{
    public RamDasher(double direction, double speed, int time, double radius, Consumer<GridEntity> onExplode, GridEntity subject){
        super(direction, speed, time, radius, onExplode, subject);
        numTargets = 1;
    }
    public RamDasher(double direction, double speed, int time, double radius, int damage, GridEntity subject){
        super(direction, speed, time, radius, damage, subject);
        numTargets = 1;
    }
    public boolean dash(){
        if(damage>0){
            damage+=10;
        }
        return super.dash();
    }
}
