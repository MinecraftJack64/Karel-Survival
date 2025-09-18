package com.karel.game;
import java.util.List;

import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class AerialBullet extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private double weight;// how much this bullet moves down
    //private int life = 10;
    ComboTracker combo;
    public AerialBullet(double rotation, double height, double speed, double reduction, GridObject source)
    {
        super(rotation, source);
        setSpeed(speed);
        setLife((int)(height/reduction)-1);
        setDamage(75);
        setRange(30);
        setCollisionMode("radius");
        setHeight(height);
        weight = reduction;
    }
    public AerialBullet(double rotation, double height, double speed, double reduction, GridObject source, ComboTracker ct)
    {
        this(rotation, height, speed, reduction, source);
        combo = ct;
    }
    public void applyPhysics(){
        setHeight(getHeight()-weight);
        super.applyPhysics();
    }
    public void checkHit(){
        //
    }
    public void die(){
        if(combo!=null)setDamage(getDamage()+combo.get());
        super.checkHit();
        explodeOn(30, 0);// just for effect for now
        if(getHitStory().size()>0&&combo!=null){
            combo.change(15);
        }
        super.die();
    }
}
