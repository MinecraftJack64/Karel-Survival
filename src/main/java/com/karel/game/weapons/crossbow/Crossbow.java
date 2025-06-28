package com.karel.game.weapons.crossbow;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Crossbow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Crossbow extends Weapon
{
    private static final int gunReloadTime = 15;
    private int ultDelay = 0;
    private double focus = 0.5;//30 framse to reach 1.5
    private double reloadDelayCount;
    private static final int ult = 1500;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            double deg = 30-(focus-0.5)*25;
            boolean ug = getAttackUpgrade()==1;
            PoisonArrow mbullet = new PoisonArrow (getHand().getTargetRotation(), focus, ug, getHolder());
            getHolder().getWorld().addObject (mbullet, getHolder().getX(), getHolder().getY());
            PoisonArrow rbullet = new PoisonArrow (getHand().getTargetRotation()-deg, focus, ug, getHolder());
            getHolder().getWorld().addObject (rbullet, getHolder().getX(), getHolder().getY());
            PoisonArrow lbullet = new PoisonArrow (getHand().getTargetRotation()+deg, focus, ug, getHolder());
            getHolder().getWorld().addObject (lbullet, getHolder().getX(), getHolder().getY());
            //bullet.move ();
            Sounds.play("crossbowshoot");
            reloadDelayCount = 0;
            if(!useGadget())focus = 0.5;
        }
    }
    public void fireUlt(){
        if(continueUlt()){
            if(ultDelay>0){
                ultDelay--;
                return;
            }
            setContinueUlt(false);
            setPlayerLockRotation(false);
            getHand().setTargetLock(false);
            fireRainingArrows(true);
        }
        else if(getUltUpgrade()==1){
            setContinueUlt(true);
            setPlayerLockRotation(true);
            getHand().setTargetLock(true);
            fireRainingArrows(true);
            ultDelay = 35;
        }else{
            fireRainingArrows(false);
        }
        Sounds.play("crossbowshoot");
    }
    public void fireRainingArrows(boolean constant){
        for(int i = 1000; i <= 1700; i+=100){
            double x = getHand().getTargetX()+(int)(Math.random()*41)-20;
            double y = getHand().getTargetY()+(int)(Math.random()*41)-20;
            RainingPoisonArrow bullet = new RainingPoisonArrow(getHolder().getAngle(x, y)+90, getHolder().distanceTo(x, y), constant?1000:i, focus, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
        }
        if(!useGadget())focus = 0.5;
    }
    public void onGadgetActivate(){
        focus = 1.5;
        setGadgetCount(4);
    }
    public int defaultGadgets(){
        return 5;
    }
    public int getUlt(){
        return ult;
    }
    public void reload(double speed){
        reloadDelayCount+=speed;
        if(reloadDelayCount>gunReloadTime&&focus<1.5){
            focus+=0.025;
            updateAmmo((int)(focus*40));
        }
    }
    public Crossbow(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(60, (int)(focus*40));
    }
    public String getName(){
        return "Crossbow";
    }
    public int getRarity(){
        return 2;
    }
}







