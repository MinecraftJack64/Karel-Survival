package com.karel.game.weapons.mortar;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.FlyingProjectile;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class MortarShell extends FlyingProjectile
{
    private double lastX, lastY;
    private boolean isGadget;
    public MortarShell(double rotation, double targetdistance, double height, GridObject source, boolean isUlt, boolean gadget)
    {
        super(rotation, targetdistance, height, source);
        isGadget = gadget;
        setImage("Weapons/crossbow/proj.png");
        scaleTexture(60);
        setRange(isUlt?50:100);
        setDamage(300);
    }
    public void applyPhysics(){
        lastX = getX();
        lastY = getY()-getHeight();
        super.applyPhysics();
        setRotation(getAngleBetween(lastX, lastY, getX(), getY()-getHeight()));
    }
    public void die(){
        if(isGadget)knockBackOnEnemies(getRange(), getRange());
        super.die();
    }
    public double getGravity(){
        return 5;
    }
}
