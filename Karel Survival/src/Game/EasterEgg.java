package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class EasterEgg extends Bullet
{
    /** The damage this bullet will deal */
    private static final int damage = 200;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 25;
    private boolean attemptedspawn = false, willspawn;
    
    public EasterEgg(double rotation, boolean chance, GridObject source)
    {
        super(rotation, source);
        setLife(25);
        setSpeed(13);
        setDamage(100);
        willspawn = chance;
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    
    public void doHit(GridEntity targ){
        Sounds.play("eggcrack");
        if(willspawn){
            Chick spawn = new Chick(1, (GridEntity)getSource());
            getWorld().addObject(spawn, getRealX(), getRealY());
        }
        attemptedspawn = true;
        super.doHit(targ);
    }
    public void die(){
        if(willspawn&&Greenfoot.getRandomNumber(2)==0&&!attemptedspawn){
            Chick spawn = new Chick(0.5, (GridEntity)getSource());
            getWorld().addObject(spawn, getRealX(), getRealY());
        }
        super.die();
    }
}
