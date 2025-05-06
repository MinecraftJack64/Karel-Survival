package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Hypnotizer extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 100;
    Necromancer notifier;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    //private double targrot;
    
    public Hypnotizer(double rotation, GridObject source)
    {
        super(rotation, source);
        //targrot = rotation;
        //addForce(new Vector(rotation, 15));
        //Greenfoot.playSound("EnergyGun.wav");
        //setTeam(source.getTeam());
        setLife(70);
        setDamage(0);
        setSpeed(7);
    }
    
    public Hypnotizer(double rotation, GridObject source, Necromancer thing)
    {
        super(rotation, source);
        //targrot = rotation;
        //addForce(new Vector(rotation, 15));
        //Greenfoot.playSound("EnergyGun.wav");
        //setTeam(source.getTeam());
        setLife(70);
        setDamage(50);
        setSpeed(7);
        notifier = thing;
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void animate()
    {
        setRealRotation(getRealRotation()+15);
    }
    public void doHit(GridEntity targ){
        if(!targ.applyEffect(new TeamSwitchEffect(getTeam(), 400, this))){
            super.doHit(targ);//if hypnosis fails, then instead do damage as usual
            return;
        }
        heal(targ, targ.getMaxHealth()-targ.getHealth());//fully heal hypnotized
        if(notifier!=null){
            notifier.notifyHypno(targ);
        }
    }
}
