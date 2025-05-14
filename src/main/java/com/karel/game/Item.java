package com.karel.game;
/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface Item  
{
    public void use();
    public void equip();
    public void unequip();
    public boolean isLocked();
    public boolean continueUse();
    public void setContinueUse(boolean s);
    public boolean continueUlt();
    public void setContinueUlt(boolean s);
    public String getName();
    public int getRarity();
}
