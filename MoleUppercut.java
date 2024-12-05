import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class MoleUppercut extends FlyingRock
{
    private boolean firstact, issuper;
    public MoleUppercut(double rotation, double targetdistance, double height, boolean sup, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setExplosionRange(50);
        setDamage(125);
        firstact = true;
        issuper = sup;
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
        super.doHit(targ);
        if(issuper){
            if(firstact)targ.knockBack(0, 0, 20, this);
            else targ.applyeffect(new StunEffect(10, this));
        }
    }
    /*public boolean covertDamage(){
        return true;
    }*/
}
