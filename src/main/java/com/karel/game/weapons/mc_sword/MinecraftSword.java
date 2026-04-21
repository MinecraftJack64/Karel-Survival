package com.karel.game.weapons.mc_sword;

import com.karel.game.Game;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.trackers.AmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Blade here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MinecraftSword extends Weapon
{
    private static final int gunReloadTime = 20, critTime = 20;
    private int reloadDelayCount, critDelay;
    private AmmoManager ammo;
    private static final int ult = 3000;
    private boolean nextdir = false;
    private int ultchargecooldown = 0;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            ammo.useAmmo();
            DiamondSword bullet = new DiamondSword(getHand().getTargetRotation(), critDelay/10.0, getAttackUpgrade(), nextdir, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            nextdir = !nextdir;
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
            critDelay = 0;
            disableSpecial();
        }
    }
    public void fireUlt(){
        critDelay = 0;
        disableSpecial();
        getHolder().addObjectHere(new EnderPearl(getHand().getTargetRotation(), Math.min(500, getHand().getTargetDistance()), getHand().getTargetDistance()+30, getHolder(), getUltUpgrade()==1));
    }
    public void onGadgetActivate(){
        setContinueGadget(true);
    }
    public int defaultGadgets(){
        return 1;
    }
    public int getUlt(){
        return ult;
    }
    public void reload(double speed){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            super.reload(speed);
            if(critDelay<critTime){
                if(critDelay==0){
                    newSpecial(critTime, 0);
                }
                critDelay++;
                updateSpecial(critDelay);
            }
        }
        if(ultchargecooldown<=0){
            chargeUlt(10);
            ultchargecooldown = 2;
        }else
            ultchargecooldown--;
    }
    public MinecraftSword(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new AmmoManager(25, 3, 3);
        setAmmo(ammo);
        chargeUltFull();
    }
    public void equip(){
        super.equip();
        Game.gameUI().newAmmo(ammo.getMaxAmmoBar(), ammo.getAmmoBar(), ammo.getMaxAmmo());
    }
    public String getName(){
        return "Diamond Sword";
    }
    public int getRarity(){
        return 8;
    }
    public BotGuide getBotGuide(){
        return new BotGuide();
    }
    public class BotGuide extends Weapon.BotGuide{
        public int getEffectiveRange(){
            return 110;
        }
        public int getNumTargets(){
            return -1;
        }
        public int getUltEffectiveRange(){
            return 500;
        }
        public int getUltIdealRange(){
            return 500;
        }
    }
}




