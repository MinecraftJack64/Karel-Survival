package com.karel.game.weapons.waterballoons;

import com.karel.game.AmmoHolder;
import com.karel.game.AmmoManager;
import com.karel.game.GridEntity;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class WaterBalloons here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaterBalloons extends Weapon implements AmmoHolder
{
    private static final int gunReloadTime = 20;
    private int reloadDelayCount;
    private static final int ult = 800;
    AmmoManager ammo;
    public void fire(){//one full ammo deals 350 damage
        if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            WaterBalloon wb = new WaterBalloon(getHand().getTargetRotation(), getHolder(), getAttackUpgrade()==1, false);
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
        BigWaterBalloon bullet = new BigWaterBalloon(getHand().getTargetRotation(), d, 200, getHolder());
        getHolder().addObjectHere(bullet);
    }
    public void fireGadgetAt(GridEntity g){
        double x = g.getX();
        double y = g.getY();
        double d = getHolder().distanceTo(x, y);
        SmallWaterBalloon bullet = new SmallWaterBalloon(getHolder().face(g, false), d, 200, getHolder());
        getHolder().addObjectHere(bullet);
    }
    public void onGadgetActivate(){
        for(GridEntity g: getHolder().getWorld().allEntities()){
            if(getHolder().isAggroTowards(g)){
                fireGadgetAt(g);
            }
        }
        setGadgetTimer(200);
    }
    public int defaultGadgets(){
        return 2;
    }
    public void reload(double g){
        if(reloadDelayCount>=gunReloadTime){
            super.reload(g);
        }else{
            reloadDelayCount++;
        }
    }
    public int getUlt(){
        return ult;
    }
    public WaterBalloons(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new AmmoManager(20, 1, 2);
        setAmmo(ammo);
    }
    public void equip(){
        super.equip();
        newAmmo(ammo.getMaxAmmoBar(), ammo.getAmmoBar(), ammo.getMaxAmmo());
    }
    public String getName(){
        return "Water Balloons";
    }
    public int getRarity(){
        return 1;
    }
}






