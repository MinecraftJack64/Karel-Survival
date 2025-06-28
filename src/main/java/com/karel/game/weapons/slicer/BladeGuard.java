package com.karel.game.weapons.slicer;

import com.karel.game.Melee;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class BladeGuard extends Melee
{
    private static final double maxRadius = 130;
    private int life = 180;
    private double degtotarg, radius;
    
    public BladeGuard(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/slicer/projGadget.png");
        scaleTexture(50);
        setMultiHit(true);
        setLife(life);
        setDamage(200);
        setNumTargets(-1);
        degtotarg = rotation;
        this.radius = 0;
    }
    
    public void applyPhysics(){
        if(life <= 0) {
            expire();
        } 
        else {
            life--;
            double centerx = getSource().getX(), centery = getSource().getY();
            setLocation(centerx+radius*Math.cos(degtotarg*Math.PI/180), centery+radius*Math.sin(degtotarg*Math.PI/180));
            setRotation(degtotarg);
            degtotarg+=6;
            checkHit();
            if(life <= 13) {
                radius -= 10; // Decrease the radius as life gets low
            }else if(radius < maxRadius) {
                radius += 10; // Increase the radius until it reaches the maximum
            }
        }
    }
}
