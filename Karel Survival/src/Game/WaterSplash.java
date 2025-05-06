package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.HashSet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class WaterSplash extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public WaterSplash(double rotation, GridObject source)
    {
        super(rotation, new HashSet<GridEntity>(), source);
    }
    public WaterSplash(double rotation, HashSet<GridEntity> h, GridObject source)
    {
        super(rotation, h, source);
        setSpeed(17);
        setLife(11);
        setDamage(30);
        setNumTargets(-1);
        setMultiHit(false);
    }
}
