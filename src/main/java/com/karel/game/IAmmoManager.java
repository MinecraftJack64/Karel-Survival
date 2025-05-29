package com.karel.game;
/**
 * Write a description of class AmmoManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface IAmmoManager
{
    public int getMaxAmmoBar();
    public int getAmmoBar();
    public void reload();
    public void reload(double speed);
    public int getAmmo();
    public int getMaxAmmo();
    public void donateAmmo(int amt);
    public void donateAmmoBar(int amt);
    public void handleAmmoOverflow();
    public boolean hasAmmo();
    public void useAmmo();
    public boolean hasAmmo(int amt);
    public void useAmmo(int amt);
}
