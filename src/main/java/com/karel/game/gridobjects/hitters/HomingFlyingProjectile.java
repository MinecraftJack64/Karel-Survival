package com.karel.game.gridobjects.hitters;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class HomingFlyingProjectile extends FlyingProjectile
{
    double originX, originY;
    private GridEntity target;
    public HomingFlyingProjectile(double rotation, double targetdistance, double height, GridObject source, GridEntity targ)
    {
        super(rotation, targetdistance, height, source);
        setIntendedTarget((GridEntity)targ);
        target = targ;
        defaultmove = false;
    }
    public void notifyWorldAdd(){
        super.notifyWorldAdd();
        setOrigin();
    }
    public void setOrigin(){
        originX = getX();
        originY = getY();
    }
    public void applyPhysics()
    {
        setLocation((target.getX()-originX)*percentDone()+originX, (target.getY()-originY)*percentDone()+originY);
        super.applyPhysics();
    }
}
