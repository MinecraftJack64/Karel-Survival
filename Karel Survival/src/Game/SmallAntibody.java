package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SmallAntibody extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    private Class target;
    public SmallAntibody(double rotation, Class targ, GridObject source)
    {
        super(rotation, source);
        setSpeed(17);
        setLife(20);
        setDamage(4);
        setNumTargets(-1);
        target = targ;
    }
    public void doHit(GridEntity targ){
        if(target!=null&&target.isInstance(targ)){
            damage(targ, getDamage()*4);
        }
        super.doHit(targ);
    }
}
