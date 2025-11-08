package com.karel.game.weapons.nailgun;

import com.karel.game.weapons.Weapon;
import com.karel.game.weapons.rock.RockChunk;
import com.karel.game.ItemHolder;

/**
 * Write a description of class NailGun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NailGun extends Weapon
{
    private static final int gunReloadTime = 30;
    private static final int waveReloadTime = 5;
    private static final int spread = 5;
    private int atkphase = 1;//1-3: shoot nails, 4 - shoot drill
    private boolean drillnext = false;
    private double reloadDelayCount;
    private int wavesleft = 0;
    private int r[] = {500, 350, 250, 100};
    private int r2[] = {500, 375, 300, 175};
    private int d[] = {100, 75, 50, 35};
    private static final int ult = 1500;
    public void fire(){
        if(continueUse()){
            if(reloadDelayCount>=waveReloadTime){
                fireWave();
                checkStopAttack();
            }
        }else{
            if (reloadDelayCount >= gunReloadTime) 
            {
                if(drillnext){
                    //shoot drill
                    reloadDelayCount = -40;
                    getAmmoBar().disable();
                    MiniDrillHead bullet = new MiniDrillHead(getHand().getTargetRotation(), getHolder());
                    getHolder().addObjectHere(bullet);
                    drillnext = false;
                }else{
                    wavesleft = atkphase;
                    setPlayerLockRotation(true);
                    setContinueUse(true);
                    fireWave();
                    checkStopAttack();
                }
            }
        }
    }
    public void checkStopAttack(){
        if(wavesleft==0){
            atkphase++;
            if(atkphase>=5){
                atkphase = 1;
            }
            setPlayerLockRotation(false);
            setContinueUse(false);
        }
    }
    public void fireWave(){
        double beginning = -atkphase*spread/2;
        for(int i = 0; i < atkphase; i++){
            double shift = beginning+i*spread;
            Nail bullet = new Nail(getAttackUpgrade()==1?r2[atkphase-1]:r[atkphase-1], d[atkphase-1], getHand().getTargetRotation()+shift/2, atkphase==4?this:null, getHolder());
            getHolder().getWorld().addObject(bullet, getHolder().getX()+shift*Math.cos(Math.PI/180*getHand().getTargetRotation()), getHolder().getY()+shift*Math.sin(Math.PI/180*getHand().getTargetRotation()));
        }
        reloadDelayCount = 0;
        wavesleft--;
    }
    public void fireUlt(){
        double x = getHand().getTargetX();
        double y = getHand().getTargetY();
        double d = Math.min(600, getHolder().distanceTo(x, y));
        RockChunk dropper = new RockChunk(getHand().getTargetRotation(), d, 200, getHolder());
        getHolder().addObjectHere(dropper);
    }
    public int getUlt(){
        return ult;
    }
    public void notifyHit(){
        drillnext = true;
    }
    public void reload(double s){
        if(reloadDelayCount<0){
            reloadDelayCount+=s;
            if(reloadDelayCount==0){
                getAmmoBar().reset();
                updateAmmo(0);
            }
        }else{
            reloadDelayCount+=s;
            if(drillnext){
                updateAmmo(gunReloadTime+1);
            }else if(wavesleft>0){
                updateAmmo(0);
            }else{
                updateAmmo(Math.min((int)reloadDelayCount, gunReloadTime));
            }
        }
    }
    public NailGun(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(gunReloadTime, (int)reloadDelayCount);
    }
    public String getName(){
        return "Nailgun";
    }
    public int getRarity(){
        return 2;
    }
}






