package com.karel.game;

/**
 * A short range projectile that teleports the shooter to the back of the enemy hit. Used by AssassinZombie
 * 
 * @author Jack Liu
 */
public class ZMeleeDagger extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    public ZMeleeDagger(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(20);
        setLife(6);
        setDamage(50);
    }
    public void doHit(GridEntity targ){
        GridEntity gs = (GridEntity)getSource();
        if(!gs.isDead()&&gs.canBePulled())gs.pullToBranch(targ, targ.getRealRotation()+90, 30);
        targ.applyEffect(new PowerPercentageEffect(0.65, 60, this));
        super.doHit(targ);
    }
}
