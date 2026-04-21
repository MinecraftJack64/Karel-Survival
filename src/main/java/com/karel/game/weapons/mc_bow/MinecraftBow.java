package com.karel.game.weapons.mc_bow;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.shields.ShieldID;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class RockCatapult here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MinecraftBow extends Weapon
{
    private static final int gunReloadTime = 70;
    private double reloadDelayCount;
    private static final int ult = 800;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 450);
            Arrow bullet = new Arrow (getHand().getTargetRotation(), d, d/5, getHolder(), getAttackUpgrade());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            Sounds.play("airtoss");
            reloadDelayCount = 0;
        }
    }
    public void fireUlt(){
        if(getUltUpgrade()==1){
            getHolder().clearEffects();
        }
        getHolder().applyShield(new UndyingTotem(new ShieldID(this), 60, getUltUpgrade()==1));
    }
    public void reload(double s){
        reloadDelayCount+=s*(getAttackUpgrade()==3?1:1.2);
        updateAmmo(Math.min((int)reloadDelayCount, gunReloadTime));
    }
    public int getUlt(){
        return ult;
    }
    public MinecraftBow(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(gunReloadTime, (int)reloadDelayCount);
    }
    public int defaultGadgets(){
        return 1;
    }
    public String getName(){
        return "Bow";
    }
    public int getRarity(){
        return 8;
    }
}






