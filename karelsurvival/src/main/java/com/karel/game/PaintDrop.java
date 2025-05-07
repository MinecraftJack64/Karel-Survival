package com.karel.game;
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class PaintDrop extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    private boolean isSuper;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public PaintDrop(double rotation, boolean isSuper, GridObject source)
    {
        super(rotation, source);
        setSpeed(20);
        setLife(isSuper?15:30);
        setDamage(isSuper?70:20);
        this.isSuper = isSuper;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(!targ.isDead()){
            targ.applyShield(getPaintShield());
        }
    }
    public Shield getPaintShield(){
        return new PaintExposureShield(new ShieldID(this), -0.5);
    }
    public static class PaintExposureShield extends PercentageShield{
        public PaintExposureShield(ShieldID myG, double strength){
            super(myG, strength, 90);
        }
        public int processDamage(int amt, GridObject source){
            remove();
            return super.processDamage(amt, source);
        }
    }
}
