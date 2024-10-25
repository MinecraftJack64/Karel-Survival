import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZombieDropper extends FlyingRock
{
    private GridObject load;
    public ZombieDropper(double rotation, double targetdistance, double height, GridObject toSpawn, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        load = toSpawn;
        //if(load instanceof GridEntity)getWorld().allEntities.add((GridEntity)load);
    }
    public double getGravity(){
        return 1;
    }
    
    public void checkAsteroidHit(){
        getWorld().addObject(load, getRealX(), getRealY());
        Sounds.play("zombiedrop");
    }
}
