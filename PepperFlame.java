import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class PepperFlame extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public PepperFlame(double rotation, int rg, GridObject source)
    {
        super(rotation, source);
        setSpeed(18);
        setLife(7+rg);
        setDamage(1);
        setNumTargets(-1);
    }
    
    public void doHit(GridEntity targ){
        targ.applyeffect(new PoisonEffect(1,1,7,this));
        super.doHit(targ);
    }
}
