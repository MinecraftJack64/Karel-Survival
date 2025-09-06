package com.karel.game;
import java.util.List;

import com.karel.game.effects.StunEffect;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class MoleUppercut extends FlyingProjectile
{
    private boolean firstact, issuper;
    private Mole mole;
    public MoleUppercut(double rotation, double targetdistance, double height, boolean sup, GridObject source, Mole tonotify)
    {
        super(rotation, targetdistance, height, source);
        setRange(75);
        setDamage(125);
        firstact = true;
        issuper = sup;
        mole = tonotify;
    }
    public void applyPhysics(){
        if(firstact){//so that it hits enemies on launch
            checkHit();
            firstact = false;
        }
        super.applyPhysics();
    }
    public double getGravity(){
        return 5;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(issuper){
            if(firstact)targ.knockBack(0, 0, 20, this);
            else targ.applyEffect(new StunEffect(10, this));
        }
    }
    public void animate(){
        setRotation(getRotation()+40);
    }
    public void die(){
        mole.notifyLand(getX(), getY());
        super.die();
    }
    /*public boolean covertDamage(){
        return true;
    }*/
}
