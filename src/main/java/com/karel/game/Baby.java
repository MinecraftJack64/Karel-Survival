package com.karel.game;

import com.karel.game.gridobjects.hitters.Bullet;
import com.karel.game.weapons.ShieldID;
import com.karel.game.weapons.gun.ProtonWave;

/**
 * Write a description of class Baby here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Baby extends GridEntity
{
    private int lvl;
    private int diff;
    private int gunReloadTime, reloadDelayCount;
    private int protonWaveReloadTime, protonWaveAmmo;
    private static int[] diffhealths = new int[]{1, 7000, 4000, 2000, 1000};
    public Baby(){
        diff = Game.currentDiff();
        if(diff==0){
            startHealthShield(new ExternalImmunityShield(new ShieldID(this), -1));
        }else{
            this.startHealth(diffhealths[diff]);
        }
        setTeam("player");
        lvl = 0;
        gunReloadTime = 12;
        protonWaveReloadTime = 12;
        reloadDelayCount = 0;
        repair();
    }
    public void behave(){
        switch(lvl){
            case 0:
            break;
            case 1:
                gunAttack();
                protonWaveAttack();
            break;
        }
    }
    public void gunAttack(){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            Bullet bullet = new Bullet(face(getNearestTarget(), false), this);
            getWorld().addObject(bullet, getX(), getY());
            protonWaveAmmo++;
            reloadDelayCount = 0;
        }
    }
    public void protonWaveAttack(){
        if(protonWaveAmmo>=protonWaveReloadTime){
            protonWaveAmmo = 0;
            ProtonWave bullet = new ProtonWave(this, false);
            addObjectHere(bullet);
        }
    }
    public void repair(){
        lvl++;
    }
    public String getName(){
        return "Turret";
    }
    public String getEntityID(){return "turret";}
}
