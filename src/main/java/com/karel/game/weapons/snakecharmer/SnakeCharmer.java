package com.karel.game.weapons.snakecharmer;

import com.karel.game.AmmoManager;
import com.karel.game.ItemHolder;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class GrenadeLauncher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SnakeCharmer extends Weapon
{
    private static final int gunReloadTime = 20;
    private int reloadDelayCount;
    private static final int ult = 2000;
    private boolean zigZag = true;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime&&getAmmo().hasAmmo()) 
        {
            //calculate aim
            double ang = 0;
            double zagrange = CharmNote.zagRange(), range = getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY());
            if(range>zagrange&&range<=zagrange*Math.sqrt(2)){
                ang = (zigZag?-1:1)*Math.toDegrees(Math.acos(zagrange/range));
            }else if(range>zagrange){
                ang = (zigZag?-1:1)*Math.toDegrees(Math.asin(zagrange/range));
            }
            getHolder().addObjectHere(new CharmNote(ang+getHand().getTargetRotation(), getHolder(), zigZag));
            zigZag = !zigZag;
            reloadDelayCount = 0;
            getAmmo().useAmmo();
        }
    }
    public void fireUlt(){
        setLocked(true);
    }
    public void reload(double speed){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            super.reload(speed);
        }
    }
    public void onGadgetActivate(){
        setGadgetCount(1);
    }
    public int defaultGadgets(){
        return 2;
    }
    public int getUlt(){
        return ult;
    }
    public SnakeCharmer(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        setAmmo(new AmmoManager(30, 3, 3));
    }
    public String getName(){
        return "Snake Charmer";
    }
    public int getRarity(){
        return 5;
    }
}






