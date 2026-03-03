package com.karel.game.weapons.droid;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

public class DroidHealLaser extends Bullet{
    public DroidHealLaser(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/laser.png");
        scaleTexture(50);
        setLife(25);
        setSpeed(20);
        setHitAllies(true, false);
        setAggression(false);
        setDamage(0);
    }
    public void doHit(GridEntity t){
        heal(t, 300);
        super.doHit(t);
    }
}
