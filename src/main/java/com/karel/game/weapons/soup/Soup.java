package com.karel.game.weapons.soup;

import com.karel.game.AmmoManager;
import com.karel.game.ItemHolder;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.PowerPercentageEffect;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Soup here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Soup extends Weapon
{
    private static final int gunReloadTime = 10;
    private AmmoManager ammo;
    private double focus = 0.5;//300 frames to reach 2
    private int reloadDelayCount;
    private static final int ult = 1600;
    private EffectID spicy = new EffectID(this, "soup au");
    public void fire(){
        if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 450);
            FlyingSoup bullet = new FlyingSoup (getHand().getTargetRotation(), d, 225+d/4, focus, getHolder());
            getHolder().addObjectHere(bullet);
            ammo.useAmmo();
            focus-=0.375;
            if(focus<0.5)focus = 0.5;
            reloadDelayCount = 0;
            if(getAttackUpgrade()==1){
                getHolder().heal(getHolder(), 40);
                getHolder().applyEffect(new PowerPercentageEffect(1.2, 15, getHolder(), spicy));
            }
        }
    }
    public void fireUlt(){
        double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 200);
        SoupDrop bullet = new SoupDrop (getHand().getTargetRotation(), d, d*2, focus, getHolder());
        getHolder().addObjectHere(bullet);
        if(getUltUpgrade()==1)focus = (focus+0.5)/2;
        else focus = 0.5;
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        if(reloadDelayCount>gunReloadTime){
            if(focus<2)focus+=0.005;
            ammo.reload();
            updateAmmo((int)(focus*40));
        }
        // chargeUlt(100);
    }
    public Soup(ItemHolder actor){
        super(actor);
        ammo = new AmmoManager(45, 3, 3);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(80, (int)(focus*40));
    }
    public String getName(){
        return "Soup";
    }
    public int getRarity(){
        return 3;
    }
}






