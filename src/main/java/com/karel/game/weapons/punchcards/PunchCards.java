package com.karel.game.weapons.punchcards;

import java.util.ArrayList;

import com.karel.game.GridObject;
import com.karel.game.ItemHolder;
import com.karel.game.physics.Dasher;
import com.karel.game.shields.ShieldID;
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
    private int gadgetCooldown = 0; // 45
    private ArrayList<Integer> punchCard = new ArrayList<Integer>();
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
        }else if (reloadDelayCount >= gunReloadTime&&getAmmo().hasAmmo()&&gadgetCooldown<=0) {
            if(continueUlt()){
                attack();
                reloadDelayCount = 0;
                getAmmo().useAmmo();
                punchCard.add(dash.getLife());
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
    public void attack(){
        getHolder().explodeOn(75, 200);
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
            punchCard.clear();
            setContinueUlt(true);
            setPlayerLockMovement(true);
            dash = new Dasher(getHand().getTargetRotation(), 10, 45, getHolder());
            ammo2.setProportional(ammo);
            setAmmo(ammo2);
            getHolder().applyShield(new RetaliationShield(new ShieldID(this), 45));
        }
    }
    public boolean canAttackDuringUlt(){
        return true;
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        gadgetCooldown = 45;
        newSpecial(gadgetCooldown, gadgetCooldown);
        for(int card: punchCard){
            getSpecialBar().addPhase(card);
        }
    }
    @Override
    public int defaultGadgets(){
        return 2;
    }
    public void reload(double at){
        reloadDelayCount++;
        super.reload(at);
        if(gadgetCooldown>0){
            if(gadgetCooldown==punchCard.get(0)){
                getHolder().explodeOn(150, 100);
                punchCard.add(punchCard.remove(0));
            }
            gadgetCooldown--;
            updateSpecial(gadgetCooldown);
            if(gadgetCooldown==0){
                disableSpecial();
            }
        }
    }
    public PunchCards(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        setAmmo(ammo);
    }
    public String getName(){
        return "Punch Cards";
    }
    public int getRarity(){
        return 1;
    }
    private class RetaliationShield extends com.karel.game.PercentageShield
    {
        public RetaliationShield(ShieldID myG, int health){
            super(myG, 0, health);
        }
        public int processDamage(int dmg, GridObject source){
            attack();
            return super.processDamage(dmg, source);
        }
    }
}






