package com.karel.game.weapons.inferno;

import com.karel.game.AmmoManager;
import com.karel.game.GridEntity;
import com.karel.game.ItemHolder;
import com.karel.game.weapons.Weapon;

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
        InfernalFlame bullet = new InfernalFlame(getHand().getTargetRotation(), getHolder());
        getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
        InfernalFlame bullet2 = new InfernalFlame(getHand().getTargetRotation()+5, getHolder());
        getHolder().getWorld().addObject (bullet2, getHolder().getRealX(), getHolder().getRealY());
        InfernalFlame bullet3 = new InfernalFlame(getHand().getTargetRotation()-5, getHolder());
        getHolder().getWorld().addObject (bullet3, getHolder().getRealX(), getHolder().getRealY());
        bullet2.setHitStory(bullet.getHitStory());
        bullet3.setHitStory(bullet.getHitStory());
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






