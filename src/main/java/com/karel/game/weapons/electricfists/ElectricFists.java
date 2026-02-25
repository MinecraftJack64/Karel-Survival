package com.karel.game.weapons.electricfists;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.hitters.Bullet;
import com.karel.game.physics.LandingHandler;
import com.karel.game.shields.ShieldID;
import com.karel.game.trackers.AmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Pointpinner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ElectricFists extends Weapon implements LandingHandler
{
    private static final int gunReloadTime = 20, shotTime = 5;
    private int shotDelay = 0;
    private double reloadDelayCount;
    private int remainingShots = 0;
    private boolean dir = false;
    private int ultPhase = 0;
    private int chargeTime = 30;
    private ProjectileSwallowShield shield;
    private static final int ult = 2000;
    public void fire(){
        if(continueUse()){
            if(shotDelay>0){
                shotDelay--;
            }else{
                if(useGadget()){
                    //
                }else{
                    ElectricFist bullet = new ElectricFist(getHand().getTargetRotation()+(remainingShots%2==(dir?1:0)?45:-45), getHolder());
                    getHolder().addObjectHere(bullet);
                }
                if(remainingShots>0){
                    shotDelay = shotTime;
                    remainingShots--;
                }else{
                    onInterrupt();
                }
            }
        }else if (reloadDelayCount >= gunReloadTime&&getAmmo().hasAmmo()) {
            if(isUsingGadget()){
                //
            }else{
                ElectricFist bullet = new ElectricFist(getHand().getTargetRotation(), getHolder());
                getHolder().addObjectHere(bullet);
            }
            shotDelay = shotTime;
            remainingShots = 3;
            setContinueUse(true);
            setPlayerLockRotation(true);
            getAmmo().useAmmo();
            Sounds.play("gunshoot");
        }
    }
    public void onInterrupt(){
        setContinueUse(false);
        setContinueUlt(false);
        setPlayerLockMovement(false);
        setPlayerLockRotation(false);
        reloadDelayCount = 0;
        dir = !dir;
    }
    public void fireUlt(){
        if(continueUlt()){
            if(ultPhase==0){
                if(chargeTime>0){
                    chargeTime--;
                }else if(chargeTime==0){
                    setPlayerLockMovement(false);
                    getHolder().initiateJump(getHand().getTargetRotation(), Math.min(200, getHand().getTargetDistance()), 100);
                    chargeTime = -1;
                }
            }else{
                if(chargeTime>0){
                    chargeTime--;
                }else if(shield.getRecord().size()>0){
                    setPlayerLockMovement(false);
                    var r = shield.getRecord();
                    var l = r.removeLast();
                    for(Bullet b: l){
                        getHolder().addObjectHere(b);
                        b.setDirection(getHand().getTargetRotation());
                    }
                }else{
                    onInterrupt();
                }
            }
        }else{
            ultPhase = 0;
            setContinueUlt(true);
            setPlayerLockMovement(true);
            chargeTime = 30;
            shield = new ProjectileSwallowShield(new ShieldID(this), chargeTime);
        }
    }
    public void doLanding(){
        if(continueUlt()){
            ultPhase = 1;
            chargeTime = 15;
            setPlayerLockMovement(true);
        }
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        getAmmo().donateAmmo(3);
        setGadgetCount(3);
    }
    @Override
    public int defaultGadgets(){
        return 2;
    }
    public void reload(double at){
        reloadDelayCount++;
        super.reload(at);
    }
    public ElectricFists(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        setAmmo(new AmmoManager(30, 3, 3));
    }
    public String getName(){
        return "Electric Fists";
    }
    public int getRarity(){
        return 2;
    }
}






