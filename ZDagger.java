import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZDagger extends ZBullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    public ZDagger(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(getSpeed());
        setLife(getLife()*2);
        setDamage(100);
    }
    public void doHit(GridEntity targ){
        targ.applyeffect(new PowerPercentageEffect(0.4, 60));
        GridEntity gs = (GridEntity)getSource();
        if(!gs.isDead()&&gs.canBePulled())gs.pullToBranch(targ, targ.getRealRotation()+90, 30);
        super.doHit(targ);
    }
}
