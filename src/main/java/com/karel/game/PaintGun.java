package com.karel.game;
import java.util.List;

import com.karel.game.weapons.Weapon;

/**
 * Write a description of class PaintGun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PaintGun extends Weapon
{
    private static final int gunReloadTime = 25;
    private AmmoManager ammo;
    private static final int ultReloadTime = 4;
    private int reloadDelayCount;
    private static final int ult = 2000;
    private int startUltCooldown = 0; // 30
    public void fire(){
        if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            for(int i = -5; i <= 5; i+=2){
                PaintDrop bullet = getAttackUpgrade()==1?new FreshPaintDrop (getHand().getTargetRotation()+i, getHolder()):new PaintDrop (getHand().getTargetRotation()+i, false, getHolder());
                getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            }
            ammo.useAmmo();
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
                    double density = 21.0/(ammo.getAmmo()+4); // 8 at 3 ammo, 6 at 1 ammo
                    double spread = 10.5;
                    if(getUltUpgrade()==1&&ammo.getAmmo()!=4){
                        spread*=4-ammo.getAmmo();
                        density*=4-ammo.getAmmo();
                    }
                    int ammoid = ammo.getAmmo();
                    for(double i = -spread; i <= spread; i+=density){
                        PaintDrop bullet = new PaintDrop (getHand().getTargetRotation()+i, true, getHolder()){
                            public double damageSecrecy(){
                                return super.damageSecrecy()*ammoid*0.165+0.4;
                            }
                        };
                        getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
                    }
                    ammo.useAmmo();
                    if(!ammo.hasAmmo()){
                        setContinueUlt(false);
                        setPlayerLockRotation(false);
                    }
                }
            }
        }else if(ammo.hasAmmo()){
            startUltCooldown = 30;
            reloadDelayCount = 0;
            setPlayerLockRotation(true);
            setContinueUlt(true);
        }else{
            cancelUltReset(); // do not allow ult to be used if no ammo
        }
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        if(!continueUlt())ammo.reload();
        updateAmmo(ammo);
    }
    public PaintGun(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new AmmoManager(35, 3, 3);
    }
    public void equip(){
        super.equip();
        newAmmo(ammo);
    }
    public String getName(){
        return "Paint Sprayer";
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




