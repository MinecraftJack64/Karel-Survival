package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZGenericBullet extends ZBullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 300;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 150;
    
    public ZGenericBullet(int dmg, double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(13);
        setLife(50);
        setDamage(dmg);
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
}
