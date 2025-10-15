package com.karel.game.gridobjects.gridentities.zombies.assassin;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.PowerPercentageEffect;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * Short range projectile that teleports the shooter behind the target, also weakening them. Used by AssassinZombie
 * @author MinecraftJack64
 */
public class ZDagger extends ZBullet
{
    /** The damage this bullet will deal */
    /** A bullet looses one life each act, and will disappear when life = 0 */
    public ZDagger(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(getSpeed());
        setLife(getLife()*2);
        setDamage(100);
        setImage("Projectiles/Bullets/dagger.png");
        setRotation(getRotation()+45);
    }
    public void doHit(GridEntity targ){
        targ.applyEffect(new PowerPercentageEffect(0.4, 60, this));
        GridEntity gs = (GridEntity)getSource();
        if(!gs.isDead()&&gs.canBePulled())gs.pullToBranch(targ, targ.getRotation()+90, 30, this);
        super.doHit(targ);
    }
}
