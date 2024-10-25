import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Asteroid extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 100;
    private int size;
    private GreenfootImage rocket = new GreenfootImage("rock.png");
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    //private double targrot;
    
    public Asteroid(double rotation, GridObject source)
    {
        this(rotation, 3, new HashSet<GridEntity>(), source);
    }
    public Asteroid(double rotation, int size, HashSet<GridEntity> h, GridObject source)
    {
        super(rotation, h, source);
        this.rocket.scale(5+size*15, 5+size*15);
        this.setImage(this.rocket);
        //targrot = rotation;
        //addForce(new Vector(rotation, 15));
        //Greenfoot.playSound("EnergyGun.wav");
        //setTeam(source.getTeam());
        setLife(200);
        setMultiHit(false);
        setDamage(size*100);
        setSpeed(3+5*size);//18, 13, 8
        this.size = size;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(size<=1){
            return;
        }
        Asteroid l = new Asteroid(getDirection()-35, size-1, getHitStory(), getSource());
        Asteroid r = new Asteroid(getDirection()+35, size-1, getHitStory(), getSource());
        getWorld().addObject(r, getRealX(), getRealY());
        getWorld().addObject(l, getRealX(), getRealY());
    }
}
