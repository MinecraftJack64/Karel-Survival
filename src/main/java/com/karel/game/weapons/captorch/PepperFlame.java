package com.karel.game.weapons.captorch;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.BurnEffect;
import com.karel.game.gridobjects.hitters.Bullet;

public class PepperFlame extends Bullet
{
    private int maxLife = 7;
    public PepperFlame(double rotation, int rg, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/captorch/proj.png");
        scaleTexture(50);
        setRotation(getRotation()-180);
        setSpeed(18);
        maxLife = 7+rg/4;
        setLife(maxLife);
        setDamage(1);
        setNumTargets(-1);
    }
    
    public void doHit(GridEntity targ){
        targ.applyEffect(new BurnEffect(1,1,(int)(17*(getLife()*1.0/maxLife)),this));
        super.doHit(targ);
    }
}
