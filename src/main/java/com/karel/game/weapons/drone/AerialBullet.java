package com.karel.game.weapons.drone;

import com.karel.game.ComboTracker;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A straight bullet shot from the drone weapon
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
        setLife((int)(height/reduction));
        setDamage(75);
        setRange(40);
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
        setHeight(0);
        if(combo!=null)setDamage(getDamage()+combo.get());
        super.checkHit();
        explodeOn(40, 0);// just for effect for now
        if(getHitStory().size()>0&&combo!=null){
            combo.change(15);
        }
        super.die();
    }
}
