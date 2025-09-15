package com.karel.game.effects;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * Knocks the target back in a set direction for a set distance, following a set height arc. Stuns the target until the knockback is complete.
 * Applied by the gridobject knockBack method.
 * ID "knockback"
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class KnockbackEffect extends StunEffect
{
    private double rot, d, h;
    public KnockbackEffect(double r, double dist, double h, GridObject source){
        super(1, source);//don't know how long the jump is
        rot = r;
        d = dist;
        this.h = h;
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/knockback.png";
    }
    public void onApply(){
        GridEntity source = getTarget();
        if(source.canBePulled()){
            source.initiateJump(rot, d, h);
            setDuration((int)(source.getPhysicsArc().getDuration()));
            super.onApply();
        }else{
            clear();
        }
    }
    public boolean isMalicious(){
        return false;
    }
}
