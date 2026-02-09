package com.karel.game.weapons.lovestrike;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

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
    private boolean isUpgrade;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public CupidArrow(double rotation, GridObject source)
    {
        this(rotation, false, source, null);
    }
    public CupidArrow(double rotation, boolean upgrade, GridObject source, Lovestrike notify)
    {
        super(rotation, source);
        setSpeed(20);
        setLife(7);
        setDamage(0);
        setNumTargets(2);
        setImage("Weapons/lovestrike/proj.png");
        setRotation(getRotation()-225);
        notifier = notify;
        isUpgrade = upgrade;
    }
    public void doHit(GridEntity targ){
        Heart bullet = new Heart(targ, isUpgrade, (GridEntity)(getSource()));
        if(notifier!=null){
            notifier.notifyHit(bullet);
        }
        getWorld().addObject(bullet, targ.getX(), targ.getY());
    }
}
