import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Pin extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    private Pointpinner me;
    public Pin(double rotation, GridObject source, Pointpinner p)
    {
        super(rotation, source);
        setSpeed(1);
        setLife(25);
        setDamage(10);
        me = p;
    }
    public void applyPhysics(){
        super.applyPhysics();
        setDamage(getDamage()+(int)(getSpeed()*2/3));
        setSpeed(getSpeed()+1);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        me.notifyHit();
    }
}
