package com.karel.game.gridobjects.hitters;

import com.karel.game.GridObject;

/**
 * A straight bullet shot from above that moves downwards
 * 
 * @author Poul Henriksen
 */
public class AerialBullet extends Bullet
{
    private double weight;
    public AerialBullet(double rotation, double height, double speed, double reduction, GridObject source)
    {
        super(rotation, source);
        setSpeed(speed);
        setLife((int)(height/reduction));
        setDamage(75);
        setRange(40);
        setCollisionMode("radius");
        setHeight(height);
        weight = reduction;
    }
    public void applyPhysics(){
        setHeight(getHeight()-weight);
        super.applyPhysics();
    }
    public void checkHit(){
        //
    }
    public void die(){
        setHeight(0);
        super.checkHit();
        explodeOn(getRange(), 0);// just for effect for now
        super.die();
    }
}
