package com.karel.game;
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class FlyingSoup extends FlyingProjectile
{
    private double focus;
    public FlyingSoup(double rotation, double targetdistance, double height, double fc, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        focus = fc;
        setRange(100);
        setDamage((int)(100*focus));
    }
    public double getGravity(){
        return 2;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        targ.applyEffect(new PoisonEffect((int)(90*focus*getPower()), 30, 3, this));
        // 185 total damage at 0.5 focus, 740 total damage at 2 focus
    }
    public void die(){
        addObjectHere(new SoupPuddle(focus, this));
        super.die();
    }
}
