package com.karel.game.weapons.sudo;

import com.karel.game.ItemHolder;
import com.karel.game.Nuke;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;
import com.karel.game.weapons.teslacoil.ChargeBomb;

/**
 * Write a description of class Sudo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sudo extends Weapon
{
    private static final int gunReloadTime = 10;
    private double reloadDelayCount;
    private static final int ult = 1;
    private SudoGauntlet zap;
    private String zombie;
    private String weapon;
    private String team;
    private int mode = 3; // 0 - current, 1 - damage, 2 - heal, 3 - move, 4 - teleport, 5 - give weapon, 6 - give effect 7 - apply team, 8 - summon, 9 - TODO: configure grid entities
    public void fire(){
        int range = 100;
        boolean scaled = false;
        int damage = 20;
        if(mode>0){
            if(zap==null){
                zap = new SudoGauntlet(getHolder());
                getHolder().getWorld().addObject(zap, getHolder().getX(), getHolder().getY());
            }
        }
        switch (mode) {
            case 0:
                if (reloadDelayCount >= gunReloadTime) {
                    ChargeBomb bullet = new ChargeBomb (getHand().getTargetRotation(), true, true, getHolder());
                    getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
                    Sounds.play("gunshoot");
                    reloadDelayCount = 5;
                    chargeUlt(1);
                }
                break;
            case 1:
                double d = getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY());
                zap.attackAt(getHand().getTargetRotation()-90, d, range, scaled, false, damage);
                break;
            case 2:
                double d2 = getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY());
                zap.healAt(getHand().getTargetRotation()-90, d2, range, scaled, false, damage);
                break;
            case 3:
                double d3 = getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY());
                zap.move(getHand().getTargetRotation()-90, d3, range, false);
                break;
            case 4:
                getHolder().pullTo(getHand().getTargetX(), getHand().getTargetY());
                break;
            case 5:
                if(reloadDelayCount >= gunReloadTime) {
                    zap.applyWeapon(getHand().getTargetRotation()-90, getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), range, false, weapon);
                    reloadDelayCount = 0;
                }
                break;
            case 6: break;
            case 7:
                double d7 = getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY());
                zap.applyTeam(getHand().getTargetRotation()-90, d7, range, false, team);
                break;
            case 8:
                if(reloadDelayCount >= gunReloadTime) {
                    zap.summonZombie(getHand().getTargetRotation()-90, getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), zombie);
                    reloadDelayCount = 0;
                }
        }
    }
    public void fireUlt(){
        int range = 100;
        boolean scaled = false;
        int damage = 20;
        String weapon = "blade";
        switch(mode){
            case 0:
                Nuke bullet = new Nuke(getHolder());
                getHolder().getWorld().addObject(bullet, getHolder().getX(), getHolder().getY());
                bullet.setPower(10000);
                Sounds.play("protonwave");
                reloadDelayCount = 0;
                break;
            case 1:
                double d = getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY());
                zap.attackAt(getHand().getTargetRotation()-90, d, range, scaled, true, damage);
                break;
            case 2:
                double d2 = getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY());
                zap.healAt(getHand().getTargetRotation()-90, d2, range, scaled, true, damage);
                break;
            case 3:
                double d3 = getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY());
                zap.move(getHand().getTargetRotation()-90, d3, range, true);
                break;
            case 4:
                getHolder().pullTo(getHand().getTargetX(), getHand().getTargetY());
                break;
            case 5:
                if(reloadDelayCount >= gunReloadTime) {
                    zap.applyWeapon(getHand().getTargetRotation()-90, getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), range, true, weapon);
                    reloadDelayCount = 0;
                }
                break;
            case 6: break;
            case 7:
                double d7 = getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY());
                zap.applyTeam(getHand().getTargetRotation()-90, d7, range, true, team);
                break;
            case 8:
                //Continuous if ult
                zap.summonZombie(getHand().getTargetRotation()-90, getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), zombie);
        }
    }
    public void setMode(int m){
        mode = m;
    }
    public void setZombie(String z){
        zombie = z;
    }
    public void setWeapon(String w){
        weapon = w;
    }
    public void setTeam(String t){
        team = t;
    }
    public int getUlt(){
        return ult;
    }
    public void reload(double mult){
        reloadDelayCount+=mult;
        chargeUlt(1);
        updateAmmo(Math.min((int)reloadDelayCount, gunReloadTime));
    }
    public Sudo(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(gunReloadTime, (int)reloadDelayCount);
    }
    public String getName(){
        return "Sudo";
    }
    public int getRarity(){
        return 0;
    }
}




