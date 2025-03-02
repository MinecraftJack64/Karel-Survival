import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class FireworkRocket extends FlyingRock
{
    public FireworkRocket(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setExplosionRange(150);
    }
    public double getGravity(){
        return 1;
    }
    public void applyPhysics(){
        if(getRealHeight()<=0){
            super.applyPhysics();
        }
        else if(percentDone()>=0.5){
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
