package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class FlyingCritter extends Bullet implements ICritter
{
    /** The damage this bullet will deal */
    private static final int damage = 200;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 25;
    private LilCreatures master;
    private int id;
    public FlyingCritter(double rotation, GridEntity source, LilCreatures spawner, int id)
    {
        super(rotation, source);
        setLife(25);
        setSpeed(20);
        setDamage(100);
        master = spawner;
        this.id = id;
    }
    public void die(){
        Critter spawn = new Critter(id, master, (GridEntity)getSource());
        addObjectHere(spawn);
        master.notifyCritterPhaseChange(id, spawn);
        super.die();
    }
}
