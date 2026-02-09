package com.karel.game.weapons.uraniumdrum;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.gridobjects.hitters.Bullet;

public class RadiatingShell extends Bullet {
    private boolean upgraded;
    public RadiatingShell(double rot, boolean upgrade, GridObject source){
        super(rot, source);
        upgraded = upgrade;
        setNumTargets(-1);
        setSpeed(5);
        setLife(400);
        setDamage(200);
    }
    public void applyPhysics(){
        if(upgraded&&getLife()%30==0){
            addObjectHere(new MicroUraniumWave(this));
        }
        super.applyPhysics();
    }
    public void doHit(GridEntity targ){
        targ.applyEffect(new PoisonEffect(200, 60, 3, this));
        super.doHit(targ);
    }
    public double damageSecrecy(){
        return super.damageSecrecy()*0.2;
    }
}
