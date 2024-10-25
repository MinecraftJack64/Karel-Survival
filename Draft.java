import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.ArrayList;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Draft extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private boolean hitfirst;
    //private int life = 10;
    private ArrayList<GridEntity> hitstory2;
    public Draft(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(22);
        setLife(20);
        setDamage(3);
        setNumTargets(-1);
        setMultiHit(false);
        hitfirst = false;
    }
    public Draft(double rotation, ArrayList<GridEntity> refhit, GridObject source)
    {
        this(rotation, source);
        hitstory2 = refhit;
    }
    public void doHit(GridEntity g){
        g.applyeffect(new SpeedPercentageEffect(0.75, 1));
        super.doHit(g);
    }
    public void onHit(GridEntity g){
        if(!hitfirst){
            hitfirst = true;
            setSpeed(15);
            setLife(Math.min(getLife(), 15));
        }
    }
}
