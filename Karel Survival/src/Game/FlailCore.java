package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class FlailCore extends Reelin
{
    private static final int naildistance = 25;//if farther, knock back, if closer, 25% extra damage
    public FlailCore(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setRange(100);
        setDamage(300);
        setSpeed(13);
        setDamageOnReturn(true);
    }
    public int getDamage(GridEntity targ){
        double multiplier = distanceTo(targ)<=naildistance?1.25:1;
        return (int)(super.getDamage(targ)*multiplier);
    }
    public void doHit(GridEntity targ){
        if(distanceTo(targ)>naildistance){
            targ.knockBack(face(targ, false), 5, 15, this);
        }
        super.doHit(targ);
    }
    public void startReturn(){
        super.startReturn();
        setDamage(100);
    }
    public double getGravity(){
        return 3;
    }
}
