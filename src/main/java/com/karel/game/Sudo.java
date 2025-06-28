package com.karel.game;

import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Sudo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sudo extends Weapon
{
    private static final int gunReloadTime = 5;
    private int reloadDelayCount;
    private static final int ult = 1;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            ChargeBomb bullet = new ChargeBomb (getHand().getTargetRotation(), true, true, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            //bullet.move ();
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
            //getHolder().getWorld().addObject(ZombieFactory.createZombie("exploding"), 50.0, 50.0);
            chargeUlt(1);
        }
    }
    public void fireUlt(){
        Nuke bullet = new Nuke(getHolder());
        getHolder().getWorld().addObject(bullet, getHolder().getX(), getHolder().getY());
        Sounds.play("protonwave");
        reloadDelayCount = 0;
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public Sudo(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Sudo";
    }
    public int getRarity(){
        return 0;
    }
}




