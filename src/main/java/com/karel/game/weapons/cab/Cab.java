package com.karel.game.weapons.cab;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.LifestealEffect;
import com.karel.game.physics.Dasher;
import com.karel.game.trackers.AmmoHolder;
import com.karel.game.trackers.AmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Reaper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cab extends Weapon implements AmmoHolder
{
    private static final int gunReloadTime = 5;
    private Dasher dash;
    private double reloadDelayCount;
    private static final int ult = 1500;
    AmmoManager ammo;
    public void fire(){//one full ammo deals 350 damage
        if(continueUse()){
            if(!dash.dash()){
                onInterrupt();
            }
        }
        else if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            dash = new RamDasher(getHand().getTargetRotation(), 2, 15, 60, (g)->{
                if(getHolder().isAggroTowards(g)){
                    int a = 15-dash.getLife();
                    g.knockBack(getHolder().face(g, false), a*15, a*5, getHolder());
                    getHolder().damage(g, 50+a*35);
                    getHolder().explodeOn(90, 100);
                }
            }, getHolder());
            setContinueUse(true);
            setPlayerLockRotation(true);
            setPlayerLockMovement(true);
            dash.dash();
            getHolder().heal(getHolder(), 125);
            Sounds.play("reap");
            reloadDelayCount = 0;
            ammo.useAmmo();
        }
    }
    public void fireUlt(){
        Sounds.play("bats");
        if(continueUlt()){
            if(!dash.dash()){
                dash = null;
                onInterrupt();
            }
        }else{
            dash = new CarryDasher(getHand().getTargetRotation(), 20, 15, 60, 100, getHolder());
            dash.dash();
            setContinueUlt(true);
            setPlayerLockRotation(true);
            setPlayerLockMovement(true);
        }
    }
    public int getUltMaxUses(){
        return 3;
    }
    public void onInterrupt(){
        if(dash instanceof CarryDasher cd){
            cd.interrupt();
        }
        dash = null;
        setContinueUse(false);
        setContinueUlt(false);
        setPlayerLockRotation(false);
        setPlayerLockMovement(false);
    }
    public int getUlt(){
        return ult;
    }
    public void reload(double d){
        if(dash==null)reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            super.reload(d);
        }
    }
    public void onGadgetActivate(){
        getHolder().explodeOnEnemies(200, (g)->{
            if(getHolder().isAggroTowards(g)){
                g.applyEffect(new LifestealEffect(100, 30, 4, getHolder(), new EffectID(this)));
            }
        });
        setGadgetTimer(150); //
    }
    public int defaultGadgets(){
        return 3;
    }
    public void notifyHit(){
        if(getUltUpgrade()==1){
            ammo.donateAmmoBar(30);
        }
    }
    public Cab(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new AmmoManager(60, 2, 3);
        setAmmo(ammo);
    }
    public String getName(){
        return "Cab";
    }
    public int getRarity(){
        return 2;
    }
}




