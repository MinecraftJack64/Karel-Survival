package com.karel.game.weapons.droid;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;

public class DroidHealLaser extends DroidLaser{
    public DroidHealLaser(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/laser.png");
        setHitAllies(true, false);
        setAggression(false);
        setDamage(0);
    }
    public void doHit(GridEntity t){
        heal(t, 300);
        super.doHit(t);
    }
}
