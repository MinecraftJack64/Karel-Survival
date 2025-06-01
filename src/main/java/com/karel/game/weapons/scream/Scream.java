package com.karel.game.weapons.scream;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.EffectID;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Scream here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scream extends Weapon
{
    private static final int gunReloadTime = 45;
    private static final int ultReloadTime = 8;
    private int reloadDelayCount;
    private static final int ult = 2000;
    private int ultAmmo = 0; // 10
    private int startUltCooldown = 0; // 30
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            for(int i = 0; i <= 0; i+=1){
                ScreamWave bullet = getProjectile(getHand().getTargetRotation()+i, 10, 50);
                getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            }
            for(int i = 0; i <= 0; i+=1){
                ScreamWave bullet = getProjectile(getHand().getTargetRotation()+i, 20, 100);
                getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            }
            for(int i = 0; i <= 0; i+=1){
                ScreamWave bullet = getProjectile(getHand().getTargetRotation()+i, 25, 50);
                getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            }
            //bullet.move ();
            reloadDelayCount = 0;
            Sounds.play("gunshoot");
        }
    }
    public void fireUlt(){
        if(continueUlt()){
            if(startUltCooldown>0){
                startUltCooldown--;
            }else{
                if(reloadDelayCount>=ultReloadTime){
                    reloadDelayCount = 0;
                    SonicBlast bullet = new SonicBlast(getHand().getTargetRotation(), getUltUpgrade()==1, useGadget(), getHolder());
                    getHolder().addObjectHere(bullet);
                    ultAmmo--;
                    if(ultAmmo<=0){
                        setContinueUlt(false);
                        setPlayerLockRotation(false);
                        getHolder().setSpeedMultiplier(1, new EffectID(this));
                    }
                }
            }
        }else{
            startUltCooldown = 30;
            reloadDelayCount = 0;
            ultAmmo = 10;
            setPlayerLockRotation(true);
            getHolder().setSpeedMultiplier(0.2, new EffectID(this));
            setContinueUlt(true);
        }
    }
    public void onGadgetActivate(){
        setGadgetCount(10);
    }
    public int defaultGadgets(){
        return 6;
    }
    public ScreamWave getProjectile(double rotation, int life, int damage){
        return getAttackUpgrade()==1?new DeafeningScreamWave(rotation, life, damage, getHolder()):new ScreamWave(rotation, life, damage, getHolder());
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public Scream(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Scream";
    }
    public int getRarity(){
        return 1;
    }
    /*public BotGuide getBotGuide(){
        return new BotGuide();
    }
    private class BotGuide extends Weapon.BotGuide{
        public static int getEffectiveRange(){
            return 600;
        }
        public static int getUltEffectiveRange(){
            return 300;
        }
        public static int getUltIdealRange(){
            return 0;
        }
    }*/
}




