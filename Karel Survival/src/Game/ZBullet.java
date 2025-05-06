package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZBullet extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 200;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    
    public ZBullet(double rotation, GridObject source)
    {
        super(rotation, source);
        setLife(50);
        setSpeed(13);
        setDamage(200);
    }
}
