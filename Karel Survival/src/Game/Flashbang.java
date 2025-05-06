package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Flashbang extends FlyingRock
{
    public Flashbang(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setRange(120);
        setDamage(0);
    }
    public void doHit(GridEntity g){
        g.applyEffect(new StunEffect(true/*TODO isFacing*/?60:45, this));
        super.doHit(g);
    }
    public void die(){
        addObjectHere(new Explosion(getRange()/60));
        super.die();
    }
    public double getGravity(){
        return 2;
    }
}
