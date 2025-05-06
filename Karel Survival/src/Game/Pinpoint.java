package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Pinpoint extends FlyingRock
{
    GridEntity target;
    double tx, ty;
    EffectID immobilize;
    public Pinpoint(boolean issuper, GridObject source)
    {
        super(0, 0, 80, source);
        setRange(35);
        setNumTargets(1);
        setDieOnHit(false);
        immobilize = new EffectID(this);
        //if(load instanceof GridEntity)getWorld().allEntities.add((GridEntity)load);
    }
    public double getGravity(){
        return 2;
    }
    public void doHit(GridEntity g){
        damage(g, 150);
        g.ground();
        g.setSpeedMultiplier(0, immobilize);
        target = g;
        tx = target.getRealX();
        ty = target.getRealY();
    }
    public void kAct(){
        if(target!=null){
            if(target.isDead())die();
            else{
                target.ground();
                target.setSpeedMultiplier(0, immobilize);
                if(distanceTo(target)<50){
                    target.pullTo(tx, ty);
                }else{
                    target.unground();
                    target.setSpeedMultiplier(1, immobilize);
                    die();
                }
            }
        }else{
            super.kAct();
        }
    }
    public void applyPhysics(){
        if(percentDone()<=0.5){setRealHeight(80);continueFrame();}
        else super.applyPhysics();
        if(getRealHeight()==0&&target==null){//didn't hit anything
            die();
        }
    }
}
