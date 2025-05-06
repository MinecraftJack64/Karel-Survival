package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class BatSwarm extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    private Reaper reaper;
    public BatSwarm(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(13);
        setLife(37);
        setDamage(100);
        setNumTargets(-1);
    }
    public BatSwarm(double rotation, Reaper reaper, GridObject source){
        this(rotation, source);
        this.reaper = reaper;
    }
    public void notifyDamage(GridEntity targ, int amt){
        super.notifyDamage(targ, amt);
        if(getSource() instanceof GridEntity)heal((GridEntity)getSource(), amt);
        if(reaper!=null)reaper.notifyHit();
    }
}
