package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Nail extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    NailGun myNG;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public Nail(int r, int d, double rotation, NailGun ng, GridObject source)
    {
        super(rotation, source);
        setSpeed(25);
        setLife(r/((int)getSpeed()));
        setDamage(d);
        myNG = ng;
    }
    public void doHit(GridEntity g){
        if(myNG!=null)myNG.notifyHit();
        super.doHit(g);
    }
}
