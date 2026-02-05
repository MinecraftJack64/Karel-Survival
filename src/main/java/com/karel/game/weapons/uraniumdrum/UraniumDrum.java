package com.karel.game.weapons.uraniumdrum;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.physics.Dasher;
import com.karel.game.trackers.AmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Gun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UraniumDrum extends Weapon
{
    private static final int gunReloadTime = 30;
    private static final int ult = 1500;
    private int unloadDelay = 0;
    private Dasher dash;
    public void fire(){
        if (getAmmo().hasAmmo()&&unloadDelay<=0&&dash==null)
        {
            UraniumWave bullet = new UraniumWave(getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            Sounds.play("gunshoot");
            getAmmo().useAmmo();
            unloadDelay = 15;
        }
    }
    public void reload(double s){
        unloadDelay--;
        super.reload(s);
    }
    public void update(){
        super.update();
        if(dash!=null&&!dash.dash()){
            dash = null;
        }
    }
    public void fireUlt(){
        if(dash!=null){
            cancelUltReset();
            return;
        }
        getAmmo().donateAmmo(1);
    }
    public void onInterrupt(){
        if(dash!=null){
            dash = null;
        }
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        dash = new Dasher(getHand().getTargetRotation(), 15, 10, getHolder());
        setGadgetTimer(60);
    }
    public UraniumDrum(ItemHolder actor){
        super(actor);
        setAmmo(new AmmoManager(gunReloadTime, 3, 3));
    }
    public int defaultGadgets(){
        return 1;
    }
    public String getName(){
        return "Uranium Drum";
    }
    public int getRarity(){
        return 3;
    }
}




