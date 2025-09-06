package com.karel.game;

import com.karel.game.effects.StunEffect;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Kiss extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    private Lovestrike notifier;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public Kiss(double rotation, GridObject source)
    {
        this(rotation, source, null);
    }
    public Kiss(double rotation, GridObject source, Lovestrike notify)
    {
        super(rotation, source);
        setSpeed(10);
        setLife(60);
        setDamage(0);
        notifier = notify;
    }
    public void doHit(GridEntity targ){
        Heart bullet = new Heart(targ, (GridEntity)(getSource()));
        if(notifier!=null){
            notifier.notifyHit(bullet);
        }
        addObjectHere(bullet);
        targ.applyEffect(new StunEffect(50, this));
        if(!((GridEntity)(getSource())).isDead()&&((GridEntity)(getSource())).canBePulled())((GridEntity)(getSource())).pullToBranch(targ, targ.getRotation()-90, 30);
        //targ.getX()-Math.cos((targ.getRotation()+90)*Math.PI/180)*30, targ.getY()-Math.sin((targ.getRotation()+90)*Math.PI/180)*30);
    }
    public void expire(){
        if(notifier!=null&&notifier.hasHeartActive()){
            if(!((GridEntity)(getSource())).isDead())((GridEntity)(getSource())).pullTo(getX(), getY());
            if(notifier!=null){
                notifier.notifyHit(null);
            }
        }
        super.expire();
    }
}
