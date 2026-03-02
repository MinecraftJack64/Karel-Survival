package com.karel.game.weapons.droid;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.trackers.AmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class GrenadeLauncher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DroidController extends Weapon
{
    private static final int gunReloadTime = 15;
    private int reloadDelayCount;
    private Droid droid;
    private static final int ult = 1200;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime&&getAmmo().hasAmmo()) 
        {
            double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 400);
            Wrench bullet = new Wrench (getHand().getTargetRotation(), d, d/2, getHolder(), droid!=null);
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            Sounds.play("airtoss");
            reloadDelayCount = 0;
            getAmmo().useAmmo();
        }
    }
    public void fireUlt(){
        if(droid==null){
            droid = new Droid(this, getHolder());
            getHolder().addObjectHere(droid);
        }else{
            //
        }
    }
    public void reload(double speed){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            super.reload(speed);
        }
    }
    public void update(){
        super.update();
        if(droid!=null&&droid.isDead()){
            droid = null;
            dischargeUlt(getUlt());
        }
        if(droid!=null&&droid.getMode()!=0){
            chargeUlt(50);
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
    public void unequip(){
        if(droid!=null){
            getHolder().getWorld().removeObject(droid);
            dischargeUlt(getUlt());
        }
        droid = null;
        super.unequip();
    }
    public DroidController(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        setAmmo(new AmmoManager(30, 3, 3));
    }
    public String getName(){
        return "Droid";
    }
    public int getRarity(){
        return 6;
    }
}






