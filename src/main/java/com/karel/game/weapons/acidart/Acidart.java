package com.karel.game.weapons.acidart;

import com.karel.game.AmmoHolder;
import com.karel.game.AmmoManager;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Acidart
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Acidart extends Weapon implements AmmoHolder
{
    private static final int gunReloadTime = 40;
    private int reloadDelayCount;
    private static final int ult = 1500;
    AmmoManager ammo;
    public void fire(){//one full ammo deals 350 damage
        if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            AcidDart wb = new AcidDart(getHand().getTargetRotation(), getHolder());
            getHolder().addObjectHere(wb);
            //bullet.move ();
            Sounds.play("fireworkshoot");
            reloadDelayCount = 0;
            ammo.useAmmo();
        }
    }
    public void fireUlt(){
        Sounds.play("crossbowshoot");
        double x = getHand().getTargetX();
        double y = getHand().getTargetY();
        double d = Math.min(600, getHolder().distanceTo(x, y));
        AcidDrop bullet = new AcidDrop(getHand().getTargetRotation(), d, d*0.75, getHolder());
        getHolder().addObjectHere(bullet);
    }
    public void onGadgetActivate(){
        setGadgetTimer(200);
    }
    public int defaultGadgets(){
        return 2;
    }
    public void reload(double g){
        if(reloadDelayCount>=gunReloadTime)super.reload(g);
        else reloadDelayCount++;
    }
    public int getUlt(){
        return ult;
    }
    public Acidart(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new AmmoManager(60, 4, 4);
        setAmmo(ammo);
    }
    public void equip(){
        super.equip();
        newAmmo(ammo.getMaxAmmoBar(), ammo.getAmmoBar(), ammo.getMaxAmmo());
    }
    public String getName(){
        return "Acidart";
    }
    public int getRarity(){
        return 1;
    }
}






