package com.karel.game.gridobjects.gridentities.zombies.cloudserver;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

public class ZBolt extends ZBullet {
    private boolean upgraded;
    public ZBolt(double rot, GridObject source, boolean upgraded){
        super(rot, source);
        setDamage(300);
        setSpeed(21);
        setLife(30);
        setNumTargets(-1);
        this.upgraded = upgraded;
    }
    public void expire(){
        if(upgraded){
            if(getNearestTarget()!=null)
            addObjectHere(new ZBolt(face(getNearestTarget(), true), this, false));
        }
        super.expire();
    }
}
