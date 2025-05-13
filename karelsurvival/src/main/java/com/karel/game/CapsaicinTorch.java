package com.karel.game;
import java.util.List;

/**
 * Write a description of class CapsaicinTorch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CapsaicinTorch extends Weapon
{
    private int reloadDelayCount;
    private int ammo = 140;
    private int maxAmmo = 140;
    private int ultammo = 0;
    private int range = 0;
    private static final int ult = 2000;
    public void fire(){
        if (ammo>0)
        {
            PepperFlame bullet = new PepperFlame(getHand().getTargetRotation(), range, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            //bullet.move ();
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
        if(range>2000){
            range = 2000;
        }
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        if(reloadDelayCount<=0)ammo+=1;
        else reloadDelayCount--;
        if(ammo>maxAmmo){
            ammo = maxAmmo;
        }
        updateAmmo(Math.min(ammo, maxAmmo));
    }
    public CapsaicinTorch(ItemHolder actor){
        super(actor);
    }
    public void equip(){
        super.equip();
        newAmmo(maxAmmo, ammo);
    }
    public String getName(){
        return "Capsaicin Torch";
    }
    public int getRarity(){
        return 0;
    }
}




