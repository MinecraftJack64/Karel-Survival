package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

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
    }
    public void doHit(GridEntity targ){
        targ.applyEffect(new PowerPercentageEffect(0.4, 60, this));
        GridEntity gs = (GridEntity)getSource();
        if(!gs.isDead()&&gs.canBePulled())gs.pullToBranch(targ, targ.getRealRotation()+90, 30);
        super.doHit(targ);
    }
}
