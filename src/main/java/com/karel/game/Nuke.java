package com.karel.game;

import com.karel.game.gridobjects.hitters.Projectile;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Nuke extends Projectile
{
    /** The damage this bullet will deal */
    Target targ;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    public Nuke()
    {
        super(null);
    }
    public Nuke(GridObject source)
    {
        super(source);
        setRotation(90);
        setHeight(2000);
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void applyPhysics()
    {
        if(targ==null){
            targ = new Target();
            getWorld().addObject(targ, getX(), getY());
        }
        if(getHeight()<=0) {
            setHeight(0);
            checkAsteroidHit();
            getWorld().removeObject(this);
            if(targ!=null){
                getWorld().removeObject(targ);
                targ = null;
            }
            super.die();
        } 
        else {
            setHeight(getHeight()-20);
        }
    }
    
    /**
     * Check whether we have hit an asteroid.
     */
    public void checkAsteroidHit()
    {
        explodeOnEnemies(1000, (g)->{doHit(g);});
        Sounds.play("rocketcrash");
    }
    public int getDamage(GridEntity targ){
        double distance = distanceTo(targ);
        return (int)(400+Math.pow(2, -(distance-1000)/100));
    }
    
    public boolean covertDamage(){
        return true;
    }
}
