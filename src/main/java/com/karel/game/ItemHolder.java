package com.karel.game;

/**
 * Allows something to hold a weapon
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface ItemHolder
{
    //Exclusive
    public void setRotationLock(boolean t);
    public void setMovementLock(boolean t);
    public void setTargetLock(boolean t);
    public double getTargetRotation();
    public double getTargetX();
    public double getTargetY();
    public boolean isMoving();
    public boolean isAttacking();
    public double getReloadSpeed();
    public GridEntity getHolder();
}


