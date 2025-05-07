package com.karel.game;
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class RainingPoisonArrow extends FlyingRock
{
    private double focus;
    public RainingPoisonArrow(double rotation, double targetdistance, double height, double fc, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        focus = fc;
        setRange(50);
    }
    public double getGravity(){
        return 5;
    }
    public void doHit(GridEntity targ){
        damage(targ, (int)(40*focus));
        targ.applyEffect(new PoisonEffect(20+(int)(20*focus/3), 40, 3, this));
        targ.applyEffect(new SpeedPercentageEffect(0.5, 120, this));
    }
    /*public boolean covertDamage(){
        return true;
    }*/
}
