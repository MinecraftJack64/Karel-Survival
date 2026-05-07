package com.karel.game.weapons.javabean;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.ReloadPercentageEffect;
import com.karel.game.effects.SoftPullEffect;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.effects.VisionPercentageEffect;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class CoffeeTorrent extends Bullet
{
    private boolean isSuper;
    
    public CoffeeTorrent(double rotation, boolean isSuper, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/scream/projUlt.png");
        scaleTexture(250);
        setRotation(rotation);
        setSpeed(20);
        setLife(30);
        setDamage(20);
        setNumTargets(-1);
        setHitAllies(true);
        this.isSuper = isSuper;
    }
    public void doHit(GridEntity targ){
        if(isAggroTowards(targ))super.doHit(targ);
        else if(isAlliedWith(targ)){
            targ.applyEffect(new ReloadPercentageEffect(1.5, 20, this));
            targ.applyEffect(new SpeedPercentageEffect(1.2, 20, this));
        }
        if(isSuper){
            //
        }
        targ.applyEffect(new SoftPullEffect(getDirection(), 3.5, 20, this, new EffectID("screamwave")));
        targ.applyEffect(new VisionPercentageEffect(0.2, 20, this));
    }
}
