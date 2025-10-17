package com.karel.game.gridobjects.gridentities.zombies.cloudserver;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.hitters.Hitter;

public class ZRainCloud extends Hitter {
    private int life = 300;
    public ZRainCloud(GridObject source){
        super(source);
        setCollisionMode("radius");
        setDamage(2);
        setHeight(200);
        setRange(50);
        setClipHits(true);
        setNumTargets(-1);
    }
    public void update(){
        if(getNearestTarget()!=null){
            double ang = face(getNearestTarget(), false);
            if(distanceTo(getNearestTarget())>50)move(ang, 1.5);
        }
        checkHit();
        super.update();
        life--;
        if(life<=0){
            die();
            getWorld().removeObject(this);
        }
    }
    public void doHit(GridEntity t){
        t.applyEffect(new SpeedPercentageEffect(0.8, 1, this));
        super.doHit(t);
    }
    public boolean isColumnar(){
        return true;
    }
}
