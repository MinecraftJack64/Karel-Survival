package com.karel.game.weapons.traps;

import java.util.ArrayList;

import com.karel.game.Dasher;
import com.karel.game.ItemHolder;
import com.karel.game.KActor;
import com.karel.game.Sounds;
import com.karel.game.weapons.EffectID;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class TrapSetter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TrapSetter extends Weapon
{
    private ArrayList<ITrap> traps = new ArrayList<ITrap>();
    private Dasher dash;
    private static final int gunReloadTime = 65;
    private static final int dashWaitTime = 12;
    private static final int bearTrapTime = 15;
    private double nextAngle = 0;
    private int jumpDelayCount = 0;
    private int bearTrapDelay = 0;
    private int remainingBearTraps = 0; // 3
    private int reloadDelayCount;
    private static final int ult = 700;
    public void fire(){
        if(continueUse()){
            if(dash!=null&&!dash.dash()){
                dash = null;
                jumpDelayCount = dashWaitTime;
                Mousetrap bullet = getAttackUpgrade()==1?new Mousetrap(getHand().getTargetRotation(), getHolder()):new Mousetrap(getHolder());
                traps.add(bullet);
                getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            }
            jumpDelayCount--;
            if(jumpDelayCount==0){
                getHolder().initiateJump(getHand().getTargetRotation()+180, 150, 30);
                getHand().setTargetLock(false);
                setContinueUse(false);
            }
        }else if (reloadDelayCount >= gunReloadTime) 
        {
            getHand().setTargetLock(true);
            dash = new Dasher(getHand().getTargetRotation(), 15, 10, getHolder());
            setContinueUse(true);
            Sounds.play("setuptrap");
            reloadDelayCount = 0;
        }
    }
    public void fireUlt(){
        if(continueUlt()){
            bearTrapDelay--;
            if(bearTrapDelay<=0){
                bearTrapDelay = bearTrapTime;
                setTrap(nextAngle);
                nextAngle+=90;
                remainingBearTraps--;
                if(remainingBearTraps<=0){
                    setContinueUlt(false);
                    getHolder().setSpeedMultiplier(1, new EffectID(this));
                }
            }
        }else{
            setContinueUlt(true);
            setTrap(getHand().getTargetRotation());
            getHolder().setSpeedMultiplier(0.5, new EffectID(this));
            nextAngle = getHand().getTargetRotation()+90;
            remainingBearTraps = 3;
            bearTrapDelay = bearTrapTime;
        }
    }
    public void setTrap(double rot){
        BearTrap bullet = getUltUpgrade()==1?new UpgradedBearTrap(rot, getHolder()):new BearTrap(rot, getHolder());
        traps.add(bullet);
        getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
        Sounds.play("setuptrap");
    }
    public void onGadgetActivate(){
        for(int i = traps.size()-1; i >= 0; i--){
            ITrap t = traps.get(i);
            if(!((KActor)t).isInWorld()){
                traps.remove(t);
            }
            else t.resnap();
        }
        setGadgetTimer(50);
    }
    public int defaultGadgets(){
        return 3;
    }
    public void reload(double s){
        reloadDelayCount+=s;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public int getUlt(){
        return ult;
    }
    public void equip(){
        super.equip();
        newAmmo(gunReloadTime, reloadDelayCount);
    }
    public TrapSetter(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public String getName(){
        return "Mouse Traps";
    }
    public int getRarity(){
        return 0;
    }
}




