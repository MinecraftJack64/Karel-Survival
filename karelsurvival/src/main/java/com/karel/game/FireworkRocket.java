package com.karel.game;
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class FireworkRocket extends FlyingRock
{
    private boolean exploded;
    public FireworkRocket(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setRange(150);
    }
    public double getGravity(){
        return 1;
    }
    public void applyPhysics(){
        if(getRealHeight()<=0){
            super.applyPhysics();
        }
        else if(percentDone()>=0.5){
            if(!exploded)explodeOn(250, 50);
            exploded = true;
            setRealHeight(getPath().getHeight(frame));
            continueFrame();
        }else{
            super.applyPhysics();
        }
    }
    public void doHit(GridEntity targ){
        targ.applyEffect(new PoisonEffect(50, 40, 5, this));
    }
}
