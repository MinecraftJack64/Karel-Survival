package com.karel.game.weapons.punchcards;

import com.karel.game.ItemHolder;
import com.karel.game.physics.Dasher;
import com.karel.game.trackers.AmmoManager;
import com.karel.game.trackers.SimpleAmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Punch Cards here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PunchCards extends Weapon
{
    private static final int gunReloadTime = 5, shotTime = 3;
    private int shotDelay = 0;
    private double reloadDelayCount;
    private int remainingShots = 0;
    private static final int ult = 2000;
    private Dasher dash;
    private AmmoManager ammo = new SimpleAmmoManager(75, 1), ammo2 = new AmmoManager(15, 3, 3);
    public void fire(){
        if(continueUse()){
            if(shotDelay>0){
                shotDelay--;
            }else{
                if(useGadget()){
                    //
                }else{
                    PunchCard bullet = new PunchCard(getHand().getTargetRotation(), getHolder());
                    getHolder().addObjectHere(bullet);
                }
                if(remainingShots>0){
                    shotDelay = shotTime;
                    remainingShots--;
                }else{
                    onInterrupt();
                }
            }
        }else if (reloadDelayCount >= gunReloadTime&&getAmmo().hasAmmo()) {
            if(continueUlt()){
                getHolder().explodeOn(75, 200);
                reloadDelayCount = 0;
                getAmmo().useAmmo();
            }else{
                PunchCard bullet = new PunchCard(getHand().getTargetRotation(), getHolder());
                getHolder().addObjectHere(bullet);
                shotDelay = shotTime;
                remainingShots = 5;
                setContinueUse(true);
                setPlayerLockRotation(true);
                getAmmo().useAmmo();
            }
        }
    }
    public void onInterrupt(){
        setContinueUse(false);
        setContinueUlt(false);
        setPlayerLockRotation(false);
        setPlayerLockMovement(false);
        reloadDelayCount = 0;
        dash = null;
        setAmmo(ammo);
    }
    public void fireUlt(){
        if(continueUlt()){
            if(!dash.dash()){
                onInterrupt();
            }
        }else{
            setContinueUlt(true);
            setPlayerLockMovement(true);
            dash = new Dasher(getHand().getTargetRotation(), 10, 45, getHolder());
            ammo2.setProportional(ammo);
            setAmmo(ammo2);
        }
    }
    public boolean canAttackDuringUlt(){
        return true;
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        //
    }
    @Override
    public int defaultGadgets(){
        return 2;
    }
    public void reload(double at){
        reloadDelayCount++;
        super.reload(at);
    }
    public PunchCards(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        setAmmo(ammo);
    }
    public String getName(){
        return "Punched Cards";
    }
    public int getRarity(){
        return 1;
    }
}






