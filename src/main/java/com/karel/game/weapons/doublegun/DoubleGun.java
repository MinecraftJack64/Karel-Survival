package com.karel.game.weapons.doublegun;

import com.karel.game.Bullet;
import com.karel.game.ItemHolder;
import com.karel.game.SimpleAmmoManager;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Gun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DoubleGun extends Weapon
{
    private static final int gunReloadTime = 7;
    private boolean quickcharge = false;
    private static final int ult = 1900;
    private int secondUltDelay = 0; // 35
    public void fire(){
        if (getAmmo().hasAmmo()) 
        {
            Bullet bullet = new DoubleBullet (getHand().getTargetRotation(), getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            Bullet bullet2 = new DoubleBullet (getHand().getTargetRotation()-180, getHolder());
            getHolder().getWorld().addObject (bullet2, getHolder().getRealX(), getHolder().getRealY());
            if(useGadget()){
                Bullet bullet3 = new DoubleBullet (getHand().getTargetRotation()+90, getHolder());
                getHolder().getWorld().addObject (bullet3, getHolder().getRealX(), getHolder().getRealY());
                Bullet bullet4 = new DoubleBullet (getHand().getTargetRotation()-90, getHolder());
                getHolder().getWorld().addObject (bullet4, getHolder().getRealX(), getHolder().getRealY());
            }
            //bullet.move ();
            Sounds.play("gunshoot");
            getAmmo().useAmmo();
            getAmmo().donateAmmoBar(quickcharge?3:0);
            quickcharge = false;
        }
    }
    public void fireUlt(){
        if(continueUlt()){
            if(secondUltDelay>0){
                secondUltDelay--;
            }else{
                NeutronWave bullet = new NeutronWave(getHolder(), getUltUpgrade()==1?2:0);
                getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
                secondUltDelay = 35;
                Sounds.play("neutronwave");
                setContinueUlt(false);
            }
        }else{
            NeutronWave bullet = new NeutronWave(getHolder(), getUltUpgrade()==1?1:0);
            getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
            Sounds.play("neutronwave");
            setContinueUlt(true);
            secondUltDelay = 35;
        }
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        setGadgetTimer(360);
    }
    public DoubleGun(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(gunReloadTime, 1));
    }
    public int defaultGadgets(){
        return 3;
    }
    public String getName(){
        return "Double Gun";
    }
    public int getRarity(){
        return 0;
    }
}




