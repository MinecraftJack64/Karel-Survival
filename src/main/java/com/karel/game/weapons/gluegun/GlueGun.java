package com.karel.game.weapons.gluegun;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.effects.SilenceEffect;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class GlueGun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GlueGun extends Weapon
{
    private double reloadDelayCount;
    private static final int ult = 800;
    private int dropsremaining = 0;
    private int explodecooldown = -1;
    private boolean ultBoost = false;
    public void fire(){
        if(continueUse()){
            double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), getRange());
            GlueDrop bullet = new GlueDrop(getHand().getTargetRotation(), d, getFocus(), getHolder());
            getHolder().addObjectHere(bullet);
            dropsremaining--;
            if(dropsremaining<=0){
                setContinueUse(false);
                setPlayerLockRotation(false);
            }
        }
        else if (reloadDelayCount >= getReloadTime()) 
        {
            double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), getRange());
            GlueDrop bullet = new GlueDrop(getHand().getTargetRotation(), d, getFocus(), getHolder()){
                public boolean covertDamage(){
                    return false;
                }
            };
            getHolder().addObjectHere(bullet);
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
            if(getNumDrops()>1){
                setContinueUse(true);
                setPlayerLockRotation(true);
                dropsremaining = getNumDrops()-1;
            }
        }
    }
    public int getReloadTime(){
        return (int)(30-getFocus()*20);
    }
    public double getRange(){
        return 275+getFocus()*100;
    }
    public int getNumDrops(){
        return (int)(getFocus()*4+1);
    }
    public double getFocus(){
        return getUltCharge()*1.0/getUlt();
    }
    public void fireUlt(){
        if(!continueUlt()){
            explodecooldown = 20;
            setContinueUlt(true);
            if(getUltUpgrade()==1){
                ultBoost = true;
            }
        }else{
            explodecooldown--;
            if(explodecooldown<=0){
                explodecooldown = -1;
                getHolder().explodeOnEnemies(225, (g)->{
                    g.applyEffect(new SpeedPercentageEffect(0.1, 170, getHolder()));
                    g.applyEffect(new SilenceEffect(170, getHolder()));
                    double d = getHolder().distanceTo(g)+100;
                    for(int i = 0; i < 5; i++){GlueDrop bullet = new GlueDrop(getHolder().face(g, false), d, 1, getHolder()){
                        public boolean covertDamage(){return false;}
                    };
                    getHolder().addObjectHere(bullet);}
                    g.knockBack(getHolder().face(g, false), 100, 30, getHolder());
                });
                setContinueUlt(false);
            }
        }
    }
    public void chargeUlt(int amt){
        if(!ultBoost){
            super.chargeUlt(amt);
        }else{
            // If ult charge 100, currently 40, and trying to add ultBoost 6, then 11 will be added
            int residuePoint = (getUlt()/2+getUltCharge())/2;
            if(getUltCharge()+amt>=residuePoint){
                int main = residuePoint-getUltCharge();
                int residue = getUltCharge()+amt-residuePoint;
                super.chargeUlt(residue+main*2);
                ultBoost = false;
            }else{
                super.chargeUlt(amt*2);
            }
        }
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        setGadgetTimer(120);
    }
    public void reload(double speed){
        reloadDelayCount+=speed;
    }
    public GlueGun(ItemHolder actor){
        super(actor);
    }
    public void equip(){
        super.equip();
    }
    public int defaultGadgets(){
        return 3;
    }
    public String getName(){
        return "Glue Gun";
    }
    public int getRarity(){
        return 2;
    }
}






