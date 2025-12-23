package com.karel.game.weapons.frostspirit;

import com.karel.game.AmmoManager;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class GrenadeLauncher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FrostSpirit extends Weapon
{
    private static final int gunReloadTime = 20;
    private int reloadDelayCount;
    private static final int ult = 2500;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime&&getAmmo().hasAmmo()) 
        {
            double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 300);
            Wreath bullet = new Wreath (getHand().getTargetRotation(), d, 150, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            Sounds.play("airtoss");
            reloadDelayCount = 0;
            getAmmo().useAmmo();
        }
    }
    public void fireUlt(){
        //
    }
    public void reload(double speed){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            super.reload(speed);
        }
    }
    public void onGadgetActivate(){
        setGadgetCount(1);
    }
    public int defaultGadgets(){
        return 2;
    }
    public int getUlt(){
        return ult;
    }
    public FrostSpirit(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        setAmmo(new AmmoManager(120, 3, 3));
    }
    public String getName(){
        return "Frost Spirit";
    }
    public int getRarity(){
        return 7;
    }
}






