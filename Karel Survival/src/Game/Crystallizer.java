package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Crystallizer extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public Crystallizer(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(10);
        setLife(35);
        setDamage(0);
        setNumTargets(3);
    }
    public void doHit(GridEntity targ){
        Crystal c = new Crystal(targ, this);
        targ.addObjectHere(c);
    }
}
