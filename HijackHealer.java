import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class HijackHealer extends FlyingRock
{
    public HijackHealer(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setRange(120);
        setDamage(300);
    }
    public void doHit(GridEntity g){
        addObjectHere(new HealCharge(0, getSource(), getDamage()));
        super.doHit(g);
    }
    public double getGravity(){
        return 2;
    }
}
