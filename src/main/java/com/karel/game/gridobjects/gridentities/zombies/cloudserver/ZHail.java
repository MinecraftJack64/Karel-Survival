package com.karel.game.gridobjects.gridentities.zombies.cloudserver;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

public class ZHail extends ZBullet {
    public ZHail(double rot, GridObject source){
        super(rot, source);
        setDamage(10);
        setSpeed(18);
        setLife(10);
    }
}
