package com.karel.game.weapons.fastfood;

import com.karel.game.AmmoHolder;
import com.karel.game.AmmoManager;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.hitters.Projectile;
import com.karel.game.shields.ShieldID;
import com.karel.game.weapons.Weapon;
import com.karel.game.weapons.sodahat.SodaPuddle;
import com.karel.game.DecayingArmorShield;

/**
 * Write a description of class Shotgun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FastFood extends Weapon implements AmmoHolder
{
    private static final int gunReloadTime = 30;
    private static final int attackDelay = 10;
    private int reloadDelayCount;
    private AmmoManager ammo;
    private static final int ult = 1200;
    private int startUltCooldown = 0; //40
    private boolean nextammosupercharged = false;
    public void fire(){//one full ammo deals 420 damage
        if(reloadDelayCount>=attackDelay&&nextammosupercharged){
            for(int deg = -20; deg<=20; deg+=10){
                Projectile mbullet = getAttackUpgrade()==1 ? (new KetchupFry(getHand().getTargetRotation()+deg, getHolder(), false)) : (new Fry(getHand().getTargetRotation()+deg, getHolder(), false));
                getHolder().getWorld().addObject (mbullet, getHolder().getX(), getHolder().getY());
            }
            Sounds.play("shotgunshoot");
            reloadDelayCount = 0;
            ammo.useAmmo();
            nextammosupercharged = false;
            setContinueUse(false);
        }else if(reloadDelayCount>=gunReloadTime&&!continueUse()&&ammo.hasAmmo()){
            for(int deg = -30; deg<=30; deg+=10){
                Projectile mbullet = getAttackUpgrade()==1 ? (new KetchupFry(getHand().getTargetRotation()+deg, getHolder(), Math.abs(deg)==20)) : (new Fry(getHand().getTargetRotation()+deg, getHolder(), Math.abs(deg)==20));
                getHolder().getWorld().addObject (mbullet, getHolder().getX(), getHolder().getY());
            }
            setContinueUse(true);
            reloadDelayCount = 0;
            nextammosupercharged = true;
        }
        
    }
    public void fireUlt(){
        if(continueUlt()){
            if(startUltCooldown>0){
                startUltCooldown--;
                return;
            }else{
                setContinueUlt(false);
                setPlayerLockMovement(false);
                getHolder().heal(getHolder(), getHolder().getMaxHealth()/3);
                ammo.donateAmmo(4);
                if(getAttackUpgrade()==1){
                    getHolder().knockBackOnEnemies(100, 100);
                }
                else getHolder().applyEffect(new SpeedPercentageEffect(1.3, 200, getHolder()));
            }
        }else{
            startUltCooldown = 40;
            setContinueUlt(true);
            setPlayerLockMovement(true);
            if(getUltUpgrade()==1){
                if(getHolder().hasShield(new ShieldID(this))){
                    getHolder().removeShield(new ShieldID(this));
                }
                getHolder().applyShield(new DecayingArmorShield(new ShieldID(this), getHolder().getMaxHealth()/5));
            }
        }
    }
    public int getUlt(){
        return ult;
    }
    public void reload(double speed){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            super.reload(speed);
        }
    }
    public void onGadgetActivate(){
        setGadgetTimer(120);
        getHolder().addObjectHere(new SodaPuddle(100, 300, 30, getHolder()));
    }
    public int defaultGadgets(){
        return 5;
    }
    public FastFood(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new AmmoManager(30, 2, 7);
        setAmmo(ammo);
    }
    public String getName(){
        return "Hamburgerer";
    }
    public int getRarity(){
        return 3;
    }
    public BotGuide getBotGuide(){
        return new BotGuide();
    }
    public class BotGuide extends Weapon.BotGuide{
        public int getEffectiveRange(){
            return 221;
        }
        public int getIdealRange(){
            return 10;
        }
        public int getUltEffectiveRange(){
            return 1000;
        }
        public boolean shouldUseUlt(){
            return ammo.getAmmo()<=3||getHolder().getHealth()<getHolder().getMaxHealth()*0.8;
        }
    }
}









