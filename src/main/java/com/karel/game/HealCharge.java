package com.karel.game;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class HealCharge extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    private GridEntity notifier;
    private int health;//amount to heal
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public HealCharge(double rotation, GridEntity source, int amt)
    {
        this(rotation, source, source, amt);
    }
    public HealCharge(double rotation, GridObject source, GridEntity notify, int amt)
    {
        super(rotation, source);
        setSpeed(20);
        setLife(1);
        setDamage(0);
        setNumTargets(-1);
        setHitAllies(true);
        setAggression(false);
        health = amt;
        notifier = notify;
    }
    @Override
    public void doHit(GridEntity targ){
        int th = targ.getMaxHealth()-targ.getHealth();
        if(th>0&&health>0){
            heal(targ, Math.min(th, health));
            health-=Math.min(th, health);
        }
    }
    @Override
    public void applyPhysics()
    {
        if(notifier!=null){
            setDirection(face(notifier, false));
            if(notifier.isDead()){expire();return;}
        }
        move(getDirection(), getSpeed());
        checkHit();
        if(getHitStory().contains(notifier)||health==0){
            finish();
        }
    }
}
