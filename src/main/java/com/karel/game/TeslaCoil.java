package com.karel.game;
import java.util.List;

import com.karel.game.weapons.Weapon;

/**
 * Write a description of class TeslaCoil here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TeslaCoil extends Weapon
{
    private static final int ult = 1800;
    private TeslaCoilZap zap, zap2;
    public void fire(){
        double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 500);
        if(zap!=null){
            zap.attackAt(getHolder().getRotation()-90, d);
        }
        if(getAttackUpgrade()==1){
            if(zap2==null){
                zap2 = new TeslaCoilZap(getHolder(), true);
                getHolder().addObjectHere(zap2);
            }
            zap2.attackAt(getHolder().getRotation()+90, d);
        }
        //show the lightning
        //Sounds.play("electicity");
    }
    public void fireUlt(){
        ChargeBomb bullet = new ChargeBomb(getHand().getTargetRotation(), getUltUpgrade()==1, useGadget(), getHolder());
        getHolder().getWorld().addObject(bullet, getHolder().getX(), getHolder().getY());
        Sounds.play("protonwave");
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        //
    }
    public void onGadgetActivate(){
        setGadgetCount(1);
    }
    public int defaultGadgets(){
        return 5;
    }
    public void equip(){
        super.equip();
        zap = new TeslaCoilZap(getHolder(), false);
        getHolder().addObjectHere(zap);
        }
    public void unequip(){
        getHolder().getWorld().removeObject(zap);
        zap = null;
        super.unequip();
    }
    public TeslaCoil(ItemHolder actor){
        super(actor);
    }
    public String getName(){
        return "Tesla Coil";
    }
    public int getRarity(){
        return 3;
    }
}






