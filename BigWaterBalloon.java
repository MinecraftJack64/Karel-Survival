import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class BigWaterBalloon extends FlyingRock
{
    private double focus;
    public BigWaterBalloon(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setExplosionRange(50);
    }
    public double getGravity(){
        return 2;
    }
    public void die(){
        for(int i = 0; i <= 360; i+=72){
            WaterBalloon wb = new WaterBalloon(i+getDirection(), this){public boolean covertDamage(){return true;}};
            addObjectHere(wb);
        }
        super.die();
    }
    public boolean covertDamage(){
        return true;
    }
}
