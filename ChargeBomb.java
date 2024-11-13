import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ChargeBomb extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public ChargeBomb(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(17);
        setLife(30);
        setDamage(500);
    }
    public void doHit(GridEntity targ){
        explodeOnEnemies(100, (g)->{
            super.doHit(g);
            g.applyeffect(new StunEffect(30, getSource()));
        });
        Explosion exp = new Explosion(1);
        getWorld().addObject(exp, getRealX(), getRealY());
        Sounds.play("explode");
    }
    public void expire(){
        doHit(null);
        super.expire();
    }
    public boolean covertDamage(){
        return true;
    }
}
