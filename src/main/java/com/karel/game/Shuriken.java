package com.karel.game;

import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Shuriken extends ZBullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 100;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    //private double targrot;
    
    public Shuriken(double rotation, GridObject source)
    {
        super(rotation, source);
        //targrot = rotation;
        //addForce(new Vector(rotation, 15));
        //Greenfoot.playSound("EnergyGun.wav");
        //setTeam(source.getTeam());
        setLife(10);
        setDamage(100);
        setSpeed(17);
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void animate()
    {
        setRealRotation(getRealRotation()+30);
    }
}
