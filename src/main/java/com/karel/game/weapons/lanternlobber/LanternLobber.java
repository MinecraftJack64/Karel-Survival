package com.karel.game.weapons.lanternlobber;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class GrenadeLauncher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LanternLobber extends Weapon
{
    private DragonFood bait;
    private int ultDuration = 0;
    private static final int gunReloadTime = 75;
    private int reloadDelayCount;
    private static final int ult = 2500;
    public void fire(){
        if(bait!=null){
            reloadDelayCount = gunReloadTime - 20;
            stopUlt();
        }else
        if (reloadDelayCount >= gunReloadTime) 
        {
            double d = Math.max(Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 600), 250);
            Lantern bullet = new Lantern (getHand().getTargetRotation(), d, 80, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            Sounds.play("airtoss");
            reloadDelayCount = 0;
        }
    }
    public void fireUlt(){
        if(bait!=null){
            cancelUltReset();
            return;
        }
        bait = new DragonFood();
        getHolder().getWorld().addObject(bait, getHand().getTargetX(), getHand().getTargetY());
        Dragon d = new Dragon(getHand().getTargetRotation(), bait, getHolder());
        getHolder().addObjectHere(d);
        setLocked(true);
        ultDuration = 300;
        newSpecial(ultDuration, ultDuration);
    }
    public void reload(double speed){
        reloadDelayCount+=speed;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public void update(){
        if(getHolder().canAttack()){
            if(ultDuration>0){
                if(bait.isInWorld())bait.setLocation(getHand().getTargetX(), getHand().getTargetY());
                else ultDuration = 1;
                ultDuration--;
                updateSpecial(ultDuration);
                if(ultDuration==0){
                    stopUlt();
                }
            }
        }
        chargeUlt(100);
    }
    public void stopUlt(){
        if(bait!=null)bait.drop();
        bait = null;
        ultDuration = 0;
        disableSpecial();
        setLocked(false);
    }
    public void onGadgetActivate(){
        setGadgetTimer(180);
    }
    public int defaultGadgets(){
        return 1;
    }
    public int getUlt(){
        return ult;
    }
    public LanternLobber(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Lantern Lobber";
    }
    public int getRarity(){
        return 8;
    }
}






