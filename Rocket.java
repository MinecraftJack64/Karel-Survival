import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Rocket extends FlyingRock
{
    private GridEntity load;
    private boolean hasLanded;
    private Target targetsymbol;
    public Rocket(double rotation, double targetdistance, double height, GridEntity toSpawn, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        load = toSpawn;
        setExplosionRange(150);
        hasLanded = false;
        load.stun();
    }
    public void applyTarget(double x, double y){
        if(targetsymbol==null){
            targetsymbol = new Target();
            getWorld().addObject(targetsymbol, x, y);
        }
    }
    public double getGravity(){
        return 5;
    }
    public void applyPhysics(){
        if(!load.isDead())load.pullTo(getRealX(), getRealY(), getRealHeight());
        super.applyPhysics();
    }
    public boolean hasLanded(){
        return hasLanded;
    }
    
    public void checkAsteroidHit(){
        load.pullTo(getRealX(), getRealY(), 0);
        load.unstun();
        hasLanded = true;
        if(targetsymbol!=null){
            getWorld().removeObject(targetsymbol);
        }
        super.checkAsteroidHit();
        Sounds.play("rocketcrash");
    }
    public void doHit(GridEntity g){
        damage(g, (int)(400-distanceTo(g)));
    }
}
