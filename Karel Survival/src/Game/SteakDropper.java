package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SteakDropper extends Dropper
{
    public SteakDropper(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, new Steak(source), source);
    }
}
