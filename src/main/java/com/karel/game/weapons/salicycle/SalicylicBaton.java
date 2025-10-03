package com.karel.game.weapons.salicycle;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.BurnEffect;
import com.karel.game.effects.EffectID;
import com.karel.game.gridobjects.hitters.Bullet;

public class SalicylicBaton extends Bullet {
    public static final int maxLife = 30;
    private int actualLife;
    private boolean normalHit;
    public SalicylicBaton(double dir, int range, GridObject source){
        super(dir, source);
        setSpeed(20);
        setNumTargets(-1);
        setLife(Math.min((int)(range/getSpeed()), maxLife));
        actualLife = getLife();
        setDamage(20);
        setRange(50);
        setCollisionMode("radius");
        setClipHits(true);
    }
    public void doHit(GridEntity t){
        normalHit = true;
        super.doHit(t);
        normalHit = false;
        if(!t.hasEffect(new EffectID(this))){
            t.applyEffect(new BurnEffect(2, 2, 10, this));
        }
    }
    public boolean covertDamage(){
        return !normalHit;
    }
    public void expire(){
        if(getSpeed()==0)super.expire();
        else{
            setSpeed(0);
            setLife(maxLife-actualLife);
        }
    }
}
