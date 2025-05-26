package com.karel.game;

import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Reaper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Reaper extends Weapon implements AmmoHolder
{
    private static final int gunReloadTime = 13;
    private Dasher dash;
    private int reloadDelayCount;
    private static final int ult = 1200;
    AmmoManager ammo;
    public void fire(){//one full ammo deals 350 damage
        if(continueUse()){
            if(!dash.dash()){
                dash = null;
                setContinueUse(false);
                setPlayerLockRotation(false);
            }
        }
        else if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            if(getAttackUpgrade()==1){
                dash = new DasherDoer(getHand().getTargetRotation(), 20, 10, 60, (g)->{
                    if(getHolder().isAggroTowards(g)){
                        if(g.willNotify(getHolder()))getHolder().heal(getHolder(), 50);
                        getHolder().damage(g, 150);
                    }
                }, getHolder());
            }else{
                dash = new DasherDoer(getHand().getTargetRotation(), 20, 10, 60, 150, getHolder());
            }
            setContinueUse(true);
            setPlayerLockRotation(true);
            dash.dash();
            getHolder().heal(getHolder(), 100);
            //bullet.move ();
            Sounds.play("reap");
            reloadDelayCount = 0;
            ammo.useAmmo();
        }
    }
    public void fireUlt(){
        Sounds.play("bats");
        BatSwarm bullet = new BatSwarm(getHand().getTargetRotation(), this, getHolder());
        getHolder().addObjectHere(bullet);
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            ammo.reload();
        }
        updateAmmo(ammo.getAmmoBar());
    }
    public void notifyHit(){
        if(getUltUpgrade()==1){
            ammo.donateAmmoBar(30);
        }
    }
    public Reaper(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new AmmoManager(60, 3, 4);
    }
    public void equip(){
        super.equip();
        newAmmo(ammo.getMaxAmmoBar(), ammo.getAmmoBar(), ammo.getMaxAmmo());
    }
    public String getName(){
        return "Reaper";
    }
    public int getRarity(){
        return 7;
    }
}




