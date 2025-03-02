import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SoupDrop extends FlyingRock
{
    private GridObject load;
    public SoupDrop(double rotation, double targetdistance, double height, double fc, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        load = new SoupBoost(fc, this);
    }
    public double getGravity(){
        return 1;
    }
    
    public void checkAsteroidHit(){
        getWorld().addObject(load, getRealX(), getRealY());
        Sounds.play("drop");
    }
}
