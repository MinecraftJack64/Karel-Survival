import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class GlueDrop extends FlyingRock
{
    private double focus;
    public GlueDrop(double rotation, double targetdistance, double focus, GridObject source)
    {
        //focus is 0 to 1
        super(rotation, targetdistance, 30, source);
        setExplosionRange(25);
        setNumTargets(focus>=0.5?2:1);
        this.focus = focus;
        setDamage((int)(focus*10+15));
    }
    public double getGravity(){
        return 2;
    }
    public void doHit(GridEntity targ){
        if(focus>0){
            targ.applyeffect(new SpeedPercentageEffect(1-focus*0.9, 15));
            targ.applyeffect(new PowerPercentageEffect(1-focus*0.8, 15));
        }
        super.doHit(targ);
    }
}
