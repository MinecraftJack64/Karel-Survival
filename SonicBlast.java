import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SonicBlast extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    private boolean isSuper, isGadget;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public SonicBlast(double rotation, boolean isSuper, boolean isGadget, GridObject source)
    {
        super(rotation, source);
        setSpeed(20);
        setRealRotation(rotation);
        getImage().scale(150, 15);
        setLife(30);
        setDamage(40);
        setNumTargets(-1);
        this.isSuper = isSuper;
        this.isGadget = isGadget;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(isSuper){
            ScreamEcho bullet = new ScreamEcho(getDirection()+180, this);
            bullet.getHitStory().add(targ);
            addObjectHere(bullet);
        }
        targ.applyEffect(new SoftPullEffect(getDirection()+(isGadget?180:0), 3.5, 20, this, new EffectID("screamwave")));
        targ.applyEffect(new SilenceEffect(20, this, new EffectID("screamwave")));
    }
}
