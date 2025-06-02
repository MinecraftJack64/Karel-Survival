package com.karel.game.weapons.scream;

import com.karel.game.Bullet;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.SilenceEffect;
import com.karel.game.SoftPullEffect;
import com.karel.game.weapons.EffectID;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SonicBlast extends Bullet
{
    private boolean isSuper, isGadget;
    
    public SonicBlast(double rotation, boolean isSuper, boolean isGadget, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/scream/projUlt.png");
        scaleTexture(125);
        setRealRotation(rotation);
        setSpeed(20);
        setLife(30);
        setDamage(40);
        setNumTargets(-1);
        this.isSuper = isSuper;
        this.isGadget = isGadget;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(isSuper){
            ScreamEcho bullet = new ScreamEcho(getDirection()+180, this);
            bullet.getHitStory().add(targ);
            addObjectHere(bullet);
        }
        targ.applyEffect(new SoftPullEffect(getDirection()+(isGadget?180:0), 3.5, 20, this, new EffectID("screamwave")));
        targ.applyEffect(new SilenceEffect(20, this, new EffectID("screamwave")));
    }
}
