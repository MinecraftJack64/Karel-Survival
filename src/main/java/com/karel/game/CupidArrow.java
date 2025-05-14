package com.karel.game;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class CupidArrow extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    private Lovestrike notifier;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public CupidArrow(double rotation, GridObject source)
    {
        this(rotation, source, null);
    }
    public CupidArrow(double rotation, GridObject source, Lovestrike notify)
    {
        super(rotation, source);
        setSpeed(20);
        setLife(7);
        setDamage(0);
        setNumTargets(-1);
        notifier = notify;
    }
    public void doHit(GridEntity targ){
        Heart bullet = new Heart(targ, (GridEntity)(getSource()));
        if(notifier!=null){
            notifier.notifyHit(bullet);
        }
        getWorld().addObject(bullet, targ.getRealX(), targ.getRealY());
    }
}
