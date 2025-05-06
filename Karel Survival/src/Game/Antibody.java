package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Antibody extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    //private Lovestrike notifier;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    private Class target;
    private LymphCannon notifier;
    public Antibody(double rotation, Class targ, GridObject source)
    {
        this(rotation, targ, null, source);
    }
    public Antibody(double rotation, Class targ, LymphCannon notify, GridObject source)
    {
        super(rotation, source);
        setSpeed(10);
        setLife(50);
        setDamage(0);
        notifier = notify;
        target = targ;
    }
    public void doHit(GridEntity targ){
        targ.applyEffect(new InfectionEffect(target!=null?(target.isInstance(targ)?150:75):100, 30, this));
        if(notifier!=null)notifier.notifyHit(targ.getClass());
    }
}
