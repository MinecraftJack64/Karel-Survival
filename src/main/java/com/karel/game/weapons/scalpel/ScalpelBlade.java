package com.karel.game.weapons.scalpel;

import com.karel.game.GridEntity;
import com.karel.game.effects.PowerPercentageEffect;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.hitters.Bullet;

public class ScalpelBlade extends Bullet {
    boolean isUpgrade, isCharged;
    Scalpel myScalpel;
    public ScalpelBlade(double rot, Scalpel scp, boolean charged, boolean upgraded){
        super(rot, scp.getHolder());
        setSpeed(25);
        setLife(5);
        setHitAllies(true);
        isCharged = charged;
        isUpgrade = upgraded;
        myScalpel = scp;
        if(charged){
            setDamage(300);
        }else{
            setDamage(50);
            setNumTargets(-1);
        }
    }
    //do up to 50% more damage when entity is at low health
    public int getDamage(GridEntity t){
        return (int)(super.getDamage(t)*(1.5-0.5*t.getPercentHealth()));
    }
    public void doHit(GridEntity targ){
        if(isAlliedWith(targ)){
            targ.applyEffect(new PowerPercentageEffect(1.1, 100, this));
            heal(targ, myScalpel.notifyHeal(isCharged?300:50));
            if(isCharged){
                if(myScalpel.getLastEnemy()==targ){
                    heal(targ, getDamage()/5);
                }
                myScalpel.setLastEnemy(targ);
            }
            return;
        }
        if(isCharged){
            targ.applyEffect(new PowerPercentageEffect(0.2, 40, this));
            if(myScalpel.getLastEnemy()==targ){
                damage(targ, getDamage()/5);
            }
            myScalpel.setLastEnemy(targ);
        }
        if(isUpgrade){
            if(isCharged)targ.applyEffect(new SpeedPercentageEffect(0.5, 40, this));
            else targ.applyEffect(new SpeedPercentageEffect(0.9, 6, this));
        }
        super.doHit(targ);
    }
    public void die(){
        if(totalHits()==0){
            heal((GridEntity)getSource(), myScalpel.notifyHeal(isCharged?300:50));
        }
        super.die();
    }
}
