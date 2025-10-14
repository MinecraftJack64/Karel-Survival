package com.karel.game.gridobjects.gridentities.zombies.chocolate;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

public class ChocolateFragment extends ZBullet {
    public ChocolateFragment(double dir, GridObject source){
        super(dir, source);
        setDamage(50);
        setSpeed(10);
        setLife(20);
    }
}
