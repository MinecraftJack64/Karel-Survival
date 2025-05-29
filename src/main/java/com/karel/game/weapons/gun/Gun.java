package com.karel.game.weapons.gun;
import java.util.List;

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
public class Gun extends Weapon
{
    private static final int gunReloadTime = 5;
    private boolean quickcharge = false;
    private static final int ult = 1750;
    public void fire(){
        if (getAmmo().hasAmmo()) 
        {
            Bullet bullet = new Bullet (getHand().getTargetRotation(), getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            //bullet.move ();
            Sounds.play("gunshoot");
            getAmmo().useAmmo();
            getAmmo().donateAmmoBar((quickcharge?3:0)+(useGadget()?2:0));
            if(getAttackUpgrade()==1){
                quickcharge = !quickcharge;
            }
        }
    }
    public void fireUlt(){
        ProtonWave bullet = new ProtonWave(getHolder(), getUltUpgrade()==1);
        getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
        Sounds.play("protonwave");
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        setGadgetTimer(120);
    }
    public Gun(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(gunReloadTime, 1));
    }
    public int defaultGadgets(){
        return 3;
    }
    public String getName(){
        return "Minigun";
    }
    public int getRarity(){
        return 0;
    }
}




