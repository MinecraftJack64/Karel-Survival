package com.karel.game.weapons.crossbow;

import com.karel.game.Bullet;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.effects.PowerPercentageEffect;
import com.karel.game.effects.SpeedPercentageEffect;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class PoisonArrow extends Bullet
{
    private boolean isupgraded;
    public PoisonArrow(double rotation, double focus, boolean isupgraded, GridObject source)
    {
        super(rotation, source);
        setImage(focus>1?"Weapons/crossbow/proj2.png":"Weapons/crossbow/proj.png");
        scaleTexture(50);
        setRotation(getRotation()-180);
        setSpeed(5+10*focus);
        setLife(20);
        setDamage((int)(40*focus));
        setNumTargets((int)((focus-0.5)*4)+1);
        this.isupgraded = isupgraded;
        //pierce through 2 if focus above 0.75, pierce through 3 if focus above 1, pierce through 4 if focus above 1.25
    }
    
    public void doHit(GridEntity asteroid){
        asteroid.applyEffect(new PoisonEffect(20+getDamage()/6, 40, getNumTargets()>1?3:5/*do more damage to last zombie due to implanted arrows*/, this));
        if(isupgraded){
            asteroid.applyEffect(new PowerPercentageEffect(0.75, (int)(getDamage()*(getNumTargets()>1?0.75:1.5)), this, new EffectID(this, "weaken")));
            asteroid.applyEffect(new SpeedPercentageEffect(0.4, (int)(getDamage()*(getNumTargets()>1?0.75:1.5)), this, new EffectID(this, "slow")));
        }
        super.doHit(asteroid);
    }
}
