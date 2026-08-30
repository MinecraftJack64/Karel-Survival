package com.karel.game.weapons.grimoire;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.SoftPullEffect;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class MagicTornado extends Bullet
{
    private int size;
    private Grimoire caster;
    public MagicTornado(double rotation, GridObject source, Grimoire g)
    {
        super(rotation, source);
        setImage("Weapons/scream/projUlt.png");
        scaleTexture(size = 25);
        setRotation(rotation);
        setSpeed(20);
        setLife(20);
        setDamage(200);
        setNumTargets(-1);
        caster = g;
    }
    public void doHit(GridEntity targ){
        caster.notifyHit(this, targ, totalHits());
        super.doHit(targ);
        if(targ.isDead()){
            caster.notifyKill(targ);
        }
        targ.applyEffect(new SoftPullEffect(getDirection(), 3.5, 20, this, new EffectID("grimoirewind")));
    }
    public void applyPhysics()
    {
        size+=5;
        scaleTexture(size);
        super.applyPhysics();
    }
    public void animate(){
        super.animate();
        setRotation(getRotation()+20);
    }
}
