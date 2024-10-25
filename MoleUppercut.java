import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class MoleUppercut extends FlyingRock
{
    private double focus;
    private boolean firstact;
    public MoleUppercut(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setExplosionRange(50);
        firstact = true;
    }
    public void applyPhysics(){
        if(firstact){//so that it hits enemies on launch
            checkAsteroidHit();
            firstact = false;
        }
        super.applyPhysics();
    }
    public double getGravity(){
        return 5;
    }
    public void doHit(GridEntity targ){
        targ.hit(100, this);
        targ.applyeffect(new PoisonEffect(20+(int)(20*focus/3), 40, 3, this));
        targ.applyeffect(new SpeedPercentageEffect(0.5, 120));
    }
    /*public boolean covertDamage(){
        return true;
    }*/
}
