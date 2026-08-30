package com.karel.game.weapons.grimoire;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class MagicWaterJet extends Bullet
{
    private Grimoire caster;
    
    public MagicWaterJet(double rotation, GridObject source, Grimoire g)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/zbullet.png");
        setLife(30);
        setSpeed(13);
        setDamage(35);
        setNumTargets(2);
        caster = g;
    }
    public void doHit(GridEntity g){
        caster.notifyHit(this, g, 0);
        g.applyEffect(new SpeedPercentageEffect(0.8, 60, this));
        super.doHit(g);
        if(g.isDead()){
            caster.notifyKill(g);
        }
        setDamage(getDamage()/2);
    }
}
