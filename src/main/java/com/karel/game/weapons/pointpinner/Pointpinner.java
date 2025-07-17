package com.karel.game.weapons.pointpinner;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;
import com.karel.game.Greenfoot;

/**
 * Write a description of class Pointpinner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pointpinner extends Weapon
{
    private static final int gunReloadTime = 30;
    private int ultcooldown = 0;//5
    private double reloadDelayCount;
    private int gadgetCooldown = 0; // 10
    private static final int ult = 2000;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            Pin bullet = new Pin(getHand().getTargetRotation(), getHolder(), getAttackUpgrade()==1?this:null);
            getHolder().addObjectHere(bullet);
            reloadDelayCount = 0;
            //bullet.move ();
            Sounds.play("gunshoot");
        }
    }
    public void fireUlt(){
        if(ultcooldown<=0){
            Pinpoint bullet = new Pinpoint(getUltUpgrade()==1, getHolder());
            getHolder().getWorld().addObject(bullet, getHand().getTargetX(), getHand().getTargetY());
            ultcooldown = 5;
        }else{
            cancelUltReset();
        }
    }
    public void notifyHit(){
        if(getAttackUpgrade()==1)reloadDelayCount+=15;
    }
    public int getUltMaxUses(){
        return 3;
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        fireGadgetWave();
        setContinueGadget(true);
        gadgetCooldown = 10;
    }
    @Override
    public void onGadgetContinue(){
        if(gadgetCooldown>0){
            gadgetCooldown--;
            if(gadgetCooldown%5==0){
                fireGadgetWave();
            }
        }else{
            setContinueGadget(false);
        }
    }
    public void fireGadgetWave(){
        for(int i = 0; i < 360; i+=30){
            Pin bullet = new Pin(i+Greenfoot.getRandomNumber(30)-15, getHolder(), 15);
            getHolder().addObjectHere(bullet);
        }
    }
    @Override
    public int defaultGadgets(){
        return 2;
    }
    public void reload(){
        reloadDelayCount+=getHolder().getReloadMultiplier();
        if(ultcooldown>0){
            ultcooldown--;
        }
        updateAmmo(Math.min((int)reloadDelayCount, gunReloadTime));
    }
    public Pointpinner(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(gunReloadTime, (int)reloadDelayCount);
    }
    public String getName(){
        return "Pointpinner";
    }
    public int getRarity(){
        return 2;
    }
}






