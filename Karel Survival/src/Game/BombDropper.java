package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class BombDropper extends Dropper
{
    public BombDropper(double rotation, double targetdistance, double height, GridObject toSpawn, GridObject source)
    {
        super(rotation, targetdistance, height, toSpawn, source);
    }
}
