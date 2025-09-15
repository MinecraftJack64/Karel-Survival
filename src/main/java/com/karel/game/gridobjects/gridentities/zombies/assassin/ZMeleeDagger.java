package com.karel.game.gridobjects.gridentities.zombies.assassin;

import com.karel.game.Bullet;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.PowerPercentageEffect;

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
        setImage("Projectiles/Bullets/dagger.png");
        setRotation(getRotation()+45);
    }
    public void doHit(GridEntity targ){
        GridEntity gs = (GridEntity)getSource();
        if(!gs.isDead()&&gs.canBePulled())gs.pullToBranch(targ, targ.getRotation()+90, 30);
        targ.applyEffect(new PowerPercentageEffect(0.65, 60, this));
        super.doHit(targ);
    }
}
