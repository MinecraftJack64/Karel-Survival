package com.karel.game.weapons.sodahat;

import com.karel.game.AmmoManager;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Pointpinner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SodaHat extends Weapon
{
    private static final int gunReloadTime = 20, shotTime = 10;
    private int shotDelay = 0;
    private double reloadDelayCount;
    private boolean nextShotHeals;
    private static final int ult = 2000;
    public void fire(){
        if(continueUse()){
            if(shotDelay>0){
                shotDelay--;
            }else{
                if(useGadget()){
                    SodaSquirt bullet = new SodaSquirt(getHand().getTargetRotation(), getHolder(), true, getAttackUpgrade()==1);
                    getHolder().addObjectHere(bullet);
                    SodaSquirt bullet2 = new SodaSquirt(getHand().getTargetRotation(), getHolder(), false, getAttackUpgrade()==1);
                    getHolder().addObjectHere(bullet2);
                }else{
                    SodaSquirt bullet = new SodaSquirt(getHand().getTargetRotation(), getHolder(), nextShotHeals, getAttackUpgrade()==1);
                    getHolder().addObjectHere(bullet);
                }
                nextShotHeals = false;
                onInterrupt();
            }
        }else if (reloadDelayCount >= gunReloadTime&&getAmmo().hasAmmo()) {
            if(isUsingGadget()){
                SodaSquirt bullet = new SodaSquirt(getHand().getTargetRotation(), getHolder(), true, getAttackUpgrade()==1);
                getHolder().addObjectHere(bullet);
                SodaSquirt bullet2 = new SodaSquirt(getHand().getTargetRotation(), getHolder(), false, getAttackUpgrade()==1);
                getHolder().addObjectHere(bullet2);
            }else{
                SodaSquirt bullet = new SodaSquirt(getHand().getTargetRotation(), getHolder(), getAmmo().getAmmo()>=3, getAttackUpgrade()==1);
                getHolder().addObjectHere(bullet);
            }
            shotDelay = shotTime;
            nextShotHeals = getAmmo().getAmmo()>=2;
            setContinueUse(true);
            setPlayerLockRotation(true);
            getAmmo().useAmmo();
            Sounds.play("gunshoot");
        }
    }
    public void onInterrupt(){
        setContinueUse(false);
        setPlayerLockRotation(false);
        reloadDelayCount = 0;
    }
    public void fireUlt(){
        if(reloadDelayCount>=gunReloadTime){
            getHolder().addObjectHere(new SodaRocket(getHand().getTargetRotation(), getHolder(), getUltUpgrade()==1));
            reloadDelayCount = 0;
        }else{
            cancelUltReset();
        }
    }
    public void notifyHit(){
        if(getAttackUpgrade()==1)reloadDelayCount+=15;
    }
    public int getUltMaxUses(){
        return 3;
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        getAmmo().donateAmmo(3);
        setGadgetCount(3);
    }
    @Override
    public int defaultGadgets(){
        return 2;
    }
    public void reload(double at){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime&&!isUsingGadget()){
            super.reload(at);
        }
    }
    public SodaHat(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        setAmmo(new AmmoManager(60, 3, 3));
    }
    public String getName(){
        return "Soda Hat";
    }
    public int getRarity(){
        return 2;
    }
}






