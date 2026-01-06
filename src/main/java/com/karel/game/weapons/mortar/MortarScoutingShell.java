package com.karel.game.weapons.mortar;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.FlyingProjectile;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class MortarScoutingShell extends FlyingProjectile
{
    private double lastX, lastY;
    public MortarScoutingShell(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setImage("Weapons/crossbow/proj.png");
        scaleTexture(60);
        setRange(200);
        setDamage(50);
    }
    public void applyPhysics(){
        lastX = getX();
        lastY = getY()-getHeight();
        super.applyPhysics();
        setRotation(getAngleBetween(lastX, lastY, getX(), getY()-getHeight()));
    }
    public double getGravity(){
        return 5;
    }
    public boolean covertDamage(){
        return true;
    }
}
