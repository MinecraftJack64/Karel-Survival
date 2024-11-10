import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
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
        super(rotation, targetdistance, 20, source);
        setExplosionRange(25);
        setNumTargets(focus>=0.5?2:1);
        this.focus = focus;
        setDamage((int)(focus*35));
    }
    public double getGravity(){
        return 2;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
    }
}
