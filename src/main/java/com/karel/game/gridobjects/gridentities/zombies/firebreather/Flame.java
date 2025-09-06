package com.karel.game.gridobjects.gridentities.zombies.firebreather;

import com.karel.game.Bullet;
import com.karel.game.GridObject;
import com.karel.game.effects.BurnEffect;
import com.karel.game.GridEntity;

public class Flame extends Bullet
{
    
    public Flame(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/flame.png");
        scaleTexture(30);
        setRotation(getRotation()-180);
        setSpeed(15);
        setLife(15);
        setDamage(15);
        setNumTargets(-1);
    }
    public void doHit(GridEntity targ){
        targ.applyEffect(new BurnEffect(10, 20, 3, getSource()));
        super.doHit(targ);
    }
}
