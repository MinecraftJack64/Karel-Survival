package com.karel.game.weapons.inferno;

import com.karel.game.AmmoManager;
import com.karel.game.ExternalImmunityShield;
import com.karel.game.GridEntity;
import com.karel.game.ItemHolder;
import com.karel.game.weapons.Weapon;
import com.karel.game.weapons.ShieldID;

import java.util.HashSet;

/**
 * Write a description of class Inferno here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Inferno extends Weapon
{
    private AmmoManager ammo;
    private int reloadDelay = 0; // 3
    private int remainingFrames;
    private int ultTeleportCooldown; // 30
    private int underworldDuration = 0; // 180
    private double tpX, tpY;
    private static final int ult = 2100;
    public void fire(){
        if(continueUse()){
            if(remainingFrames>0){
                fireWave();
                remainingFrames--;
            }else{
                setContinueUse(false);
                setPlayerLockRotation(false);
                reloadDelay = 3;
            }
        }
        else if (ammo.hasAmmo()&&reloadDelay<=0)
        {
            fireWave();
            setContinueUse(true);
            setPlayerLockRotation(true);
            remainingFrames = 3;
            ammo.useAmmo();
        }
    }
    public void fireWave(){
        InfernalFlame bullet = getProjectile(0);
        getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
        InfernalFlame bullet2 = getProjectile(5);
        getHolder().getWorld().addObject (bullet2, getHolder().getRealX(), getHolder().getRealY());
        InfernalFlame bullet3 = getProjectile(-5);
        getHolder().getWorld().addObject (bullet3, getHolder().getRealX(), getHolder().getRealY());
        bullet2.setHitStory(bullet.getHitStory());
        bullet3.setHitStory(bullet.getHitStory());
    }
    public InfernalFlame getProjectile(double rotation){
        return getAttackUpgrade()==1?new SoulInfernalFlame(getHand().getTargetRotation()+rotation, getHolder()):new InfernalFlame(getHand().getTargetRotation()+rotation, getHolder());
    }
    public void fireUlt(){
        if(continueUlt()){
            if(ultTeleportCooldown>0){
                ultTeleportCooldown--;
            }else{
                getHolder().pullTo(tpX, tpY);
                for(int j = 0; j < 3; j++){
                    HashSet<GridEntity> hs = null;
                    for(int i = 0; i < 360; i+=5){// create 3 waves of attack bullets
                        InfernalFlame bullet = new InfernalFlame(getHand().getTargetRotation()+i, getHolder()){
                            public boolean covertDamage(){
                                return true;
                            }
                        };
                        getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
                        if(hs!=null)bullet.setHitStory(hs);
                        else hs = bullet.getHitStory();
                    }
                }
                setPlayerLockMovement(false);
                setPlayerLockRotation(false);
                setContinueUlt(false);
            }
        }else{
            ultTeleportCooldown = 30;
            tpX = getHand().getTargetX();
            tpY = getHand().getTargetY();
            if(getUltUpgrade()==1){
                getHolder().applyShield(new BurningShield(new ShieldID(this), 0.4, 90));
            }
            setPlayerLockMovement(true);
            setPlayerLockRotation(true);
            setContinueUlt(true);
        }
    }
    public int getUlt(){
        return ult;
    }
    public void reload(double speed){
        super.reload(speed);
        if(reloadDelay>0){
            reloadDelay--;
        }
    }
    public void onGadgetContinue(){
        if(underworldDuration>0){
            if(underworldDuration%3==0)getHolder().addObjectHere(new FireTrail(getHolder()));
            underworldDuration--;
            if(underworldDuration<=0){
                getHolder().setRealHeight(0);
                getHolder().removeShield(new ShieldID(this, "underworld"));
                setContinueGadget(false);
            }
        }
    }
    public void onGadgetActivate(){
        underworldDuration = 180;
        setContinueGadget(true);
        getHolder().setRealHeight(-10);
        getHolder().applyShield(new ExternalImmunityShield(new ShieldID(this, "underworld"), 360));
    }
    public int defaultGadgets(){
        return 1;
    }
    public Inferno(ItemHolder actor){
        super(actor);
        chargeUlt(getUlt()/2);
        ammo = new AmmoManager(50, 3, 3);
        setAmmo(ammo);
    }
    public void equip(){
        super.equip();
        newAmmo(ammo);
    }
    public String getName(){
        return "Inferno";
    }
    public int getRarity(){
        return 1;
    }
}
