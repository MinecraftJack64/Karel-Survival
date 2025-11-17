package com.karel.game.weapons.gale;

import com.karel.game.Melee;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class TornadoDebris extends Melee
{
    private static final double maxRadius = 100;
    private int life = 200;
    private double degtotarg, radius;
    
    public TornadoDebris(double rotation, GridObject source)
    {
        super(rotation, source);
        setMultiHit(true);
        setLife(life);
        setDamage(50);
        setNumTargets(-1);
        degtotarg = rotation;
        this.radius = 0;
    }
    
    public void applyPhysics(){
        if(life <= 0||!getSource().isInWorld()) {
            expire();
        } 
        else {
            life--;
            double centerx = getSource().getX(), centery = getSource().getY();
            setLocation(centerx+radius*Math.cos(degtotarg*Math.PI/180), centery+radius*Math.sin(degtotarg*Math.PI/180));
            setRotation(degtotarg);
            degtotarg+=6;
            checkHit();
            if(life <= 10) {
                radius -= 10; // Decrease the radius as life gets low
            }else if(radius < maxRadius) {
                radius += 10; // Increase the radius until it reaches the maximum
            }
        }
    }
    public boolean covertDamage(){
        return true;
    }
}
