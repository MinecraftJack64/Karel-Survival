package com.karel.game.weapons.catclaw;

import com.karel.game.Bullet;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SwipingClaw extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 20;
    private double degtotarg, radius;
    private double offsetx, offsety;
    
    public SwipingClaw(double rotation, double radius, GridObject source)
    {
        super(rotation, source);
        setSpeed(17);
        setLife(life);
        setDamage(75);
        setNumTargets(-1);
        degtotarg = rotation-180;
        this.radius = radius;
        offsetx = radius*Math.cos(degtotarg*Math.PI/180);
        offsety = radius*Math.sin(degtotarg*Math.PI/180);
    }
    
    public void applyPhysics(){
        if(life <= 0) {
            expire();
        } 
        else {
            life--;
            setDamage(40+(int)(0.5*distanceTo(getSource())));
            double centerx = offsetx+getSource().getX(), centery = offsety+getSource().getY();
            setLocation(centerx-radius*Math.cos(degtotarg*Math.PI/180), centery-radius*Math.sin(degtotarg*Math.PI/180));
            setRotation(degtotarg+90);
            degtotarg+=18;
            checkHit();
        }
    }
    public double damageSecrecy(){
        return super.damageSecrecy()*0.2;
    }
}
