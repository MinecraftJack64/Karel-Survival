package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Flame extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public Flame(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(15);
        setLife(15);
        setDamage(15);
        setNumTargets(-1);
    }
}
