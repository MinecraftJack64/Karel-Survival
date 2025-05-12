import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ScreamEcho extends Bullet
{
    /** The damage this bullet will deal */
    private static final double range = 300;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    public ScreamEcho(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(20);
        setRealRotation(rotation);
        getImage().scale(150, 15);
        setLife(10);
        setDamage(20);
        setNumTargets(1);
    }
    public boolean covertDamage(){
        return true;
    }
}
