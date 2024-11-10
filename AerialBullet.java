import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class AerialBullet extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private double weight;
    //private int life = 10;
    
    public AerialBullet(double rotation, double height, double speed, double reduction, GridObject source)
    {
        super(rotation, source);
        setSpeed(speed);
        setLife((int)(height/reduction));
        setDamage(0);
        setRealHeight(height);
        weight = reduction;
    }
    public void applyPhysics(){
        setRealHeight(getRealHeight()-weight);
        super.applyPhysics();
    }
    public void die(){
        explodeOn(30, 100);
        super.die();
    }
}
