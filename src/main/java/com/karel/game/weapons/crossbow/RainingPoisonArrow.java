package com.karel.game.weapons.crossbow;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.hitters.FlyingProjectile;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class RainingPoisonArrow extends FlyingProjectile
{
    private double lastX, lastY;
    private double focus;
    private boolean firsthit;
    public RainingPoisonArrow(double rotation, double targetdistance, double height, double fc, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setImage(fc>1?"Weapons/crossbow/proj2.png":"Weapons/crossbow/proj.png");
        scaleTexture(60);
        focus = fc;
        firsthit = false;
        setRange(50);
    }
    public void applyPhysics(){
        lastX = getX();
        lastY = getY()-getHeight();
        super.applyPhysics();
        setRotation(getAngleBetween(lastX, lastY, getX(), getY()-getHeight()));
    }
    public double getGravity(){
        return 5;
    }
    public void doHit(GridEntity targ){
        damage(targ, (int)(40*focus));
        firsthit = true;
        targ.applyEffect(new PoisonEffect(20+(int)(20*focus/3), 40, 3, this));
        targ.applyEffect(new SpeedPercentageEffect(0.5, 120, this));
    }
    public void notifyDamage(GridEntity source, int amt){
        if(!firsthit)super.notifyDamage(source, amt);
    }
}
