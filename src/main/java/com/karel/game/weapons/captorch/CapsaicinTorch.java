package com.karel.game.weapons.captorch;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class CapsaicinTorch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CapsaicinTorch extends Weapon
{
    private int reloadDelayCount;
    private double ammo = 140;
    private int maxAmmo = 140;
    private int ultammo = 0;
    private int range = 0;
    //TODO: show fire bar
    private static final int ult = 2000;
    public void fire(){
        if (ammo>0)
        {
            PepperFlame bullet = new PepperFlame(getHand().getTargetRotation(), range, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            Sounds.play("gunshoot");
            ammo--;
            if(range>0)range--;
            reloadDelayCount = 7;// time between ammo reloads
        }
    }
    public void fireUlt(){
        if(continueUlt()){
            ultammo--;
            SuperPepperFlame bullet = new SuperPepperFlame(getHand().getTargetRotation(), getHolder(), this);
            getHolder().addObjectHere(bullet);
            if(ultammo<=0){
                setContinueUlt(false);
                setPlayerLockRotation(false);
            }
        }else{
            setPlayerLockRotation(true);
            setContinueUlt(true);
            ultammo+=5;
        }
    }
    public void notifyHit(){
        range+=8;
        if(range>400){
            range = 400;
        }
    }
    public int getUlt(){
        return ult;
    }
    public void reload(double speed){
        speed*=0.8;
        if(reloadDelayCount<=0)ammo+=speed;
        else reloadDelayCount--;
        if(ammo>maxAmmo){
            ammo = maxAmmo;
        }
        updateAmmo(Math.min((int)ammo, maxAmmo));
    }
    public CapsaicinTorch(ItemHolder actor){
        super(actor);
    }
    public void equip(){
        super.equip();
        newAmmo(maxAmmo, (int)ammo);
    }
    public String getName(){
        return "Capsaicin Torch";
    }
    public int getRarity(){
        return 0;
    }
    public BotGuide getBotGuide(){
        return new BotGuide();
    }
    public class BotGuide extends Weapon.BotGuide{
        public int getEffectiveRange(){
            return (int)(126+4.5*range);
        }
        public int getNumTargets(){
            return -1;
        }
        public int getUltEffectiveRange(){
            return 600;
        }
        public int getUltNumTargets(){
            return -1;
        }
        //AVERAGE SPEED OF PROJECTILE
        public double getLead(double d){
            return 18;
        }
        public double getUltLead(){
            return 15;
        }
    }
}




