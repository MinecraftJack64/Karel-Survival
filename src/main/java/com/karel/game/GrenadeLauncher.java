package com.karel.game;

import com.karel.game.weapons.ShieldID;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class GrenadeLauncher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GrenadeLauncher extends Weapon
{
    private static final int gunReloadTime = 60;
    private int reloadDelayCount;
    private int sid;
    private static final int ult = 1000;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 300);
            Grenade bullet = getAttackUpgrade()==1?new UpgradedGrenade(getHand().getTargetRotation(), d, d/4, getHolder()):new Grenade (getHand().getTargetRotation(), d, d/4, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            if(useGadget()){
                Flashbang bullet2 = new Flashbang(getHand().getTargetRotation(), d, d/4, getHolder());
                getHolder().getWorld().addObject (bullet2, getHolder().getRealX(), getHolder().getRealY());
            }
            Sounds.play("airtoss");
            reloadDelayCount = 0;
        }
    }
    public void fireUlt(){
        getHolder().applyShield(new ArmorShield(new ShieldID(this, Integer.toString(sid)), 150));
        sid++;
        if(getUltUpgrade()==1){
            for(int i = 0; i < 360; i+=90){
                getHolder().addObjectHere(new Sandbag(getHolder().getTargetRotation()+i, getHolder()));
            }
        }
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
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
    public GrenadeLauncher(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Grenades";
    }
    public int getRarity(){
        return 1;
    }
}






