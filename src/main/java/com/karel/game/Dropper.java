package com.karel.game;

import com.karel.game.gridobjects.hitters.FlyingProjectile;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Dropper extends FlyingProjectile
{
    private GridObject load;
    public Dropper(double rotation, double targetdistance, double height, GridObject toSpawn, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        load = toSpawn;
        setDamage(0);
    }
    public double getGravity(){
        return 1;
    }
    public void setLoad(GridObject n){
        load = n;
    }
    
    public void checkHit(){
        getWorld().addObject(load, getX(), getY());
        Sounds.play("zombiedrop");
        super.checkHit();
    }
}
