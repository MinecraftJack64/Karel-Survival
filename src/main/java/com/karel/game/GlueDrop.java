package com.karel.game;
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class GlueDrop extends FlyingRock
{
    private double focus;
    public GlueDrop(double rotation, double targetdistance, double focus, GridObject source)
    {
        //focus is 0 to 1
        super(rotation, targetdistance, Math.max(30, 30*focus), source);
        setRange(25);
        setNumTargets(focus>=0.5?2:1);
        this.focus = focus;
        setDamage((int)(focus*10+15));
    }
    public double getGravity(){
        return 2;
    }
    public void doHit(GridEntity targ){
        if(focus>0){
            targ.applyEffect(new SpeedPercentageEffect(1-focus*0.9, 15, this, new EffectID(this, "slow")));
            targ.applyEffect(new PowerPercentageEffect(1-focus*0.8, 15, this, new EffectID(this, "weaken")));
        }
        super.doHit(targ);
    }
    public boolean covertDamage(){
        return true;
    }
}
