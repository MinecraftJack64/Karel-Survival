package com.karel.game.weapons.mc_tnt;

import com.karel.game.GridObject;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.trackers.AmmoHolder;
import com.karel.game.trackers.AmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Shotgun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MinecraftTNT extends Weapon implements AmmoHolder
{
    private static final int gunReloadTime = 20;
    private int reloadDelayCount;
    private AmmoManager ammo;
    private static final int ult = 2000;
    public void fire(){//one full ammo deals 350 damage
        if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            double d = Math.min(350, getHand().getTargetDistance());
            double r = getHand().getTargetRotation()-90;
            getHolder().getWorld().addObject(new FaSFire(getHolder()), getHolder().getX()+getHolder().getBranchX(r, d), getHolder().getY()+getHolder().getBranchY(r, d));
            Sounds.play("shotgunshoot");
            reloadDelayCount = 0;
            ammo.useAmmo();
        }
    }
    public void fireUlt(){
        double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 400);
        TNT lasso = new TNT(getHand().getTargetRotation(), d, getHolder(), getUltUpgrade()==1);
        getHolder().addObjectHere((GridObject)lasso);
        Sounds.play("lassoshoot");
    }
    public int getUlt(){
        return ult;
    }
    public void reload(double speed){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            super.reload(speed);
        }
    }
    public MinecraftTNT(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new AmmoManager(60, 2, 3);
        setAmmo(ammo);
    }
    public String getName(){
        return "Flint and Steel";
    }
    public int getRarity(){
        return 8;
    }
}









