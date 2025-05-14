package com.karel.game;
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class FreshPaintDrop extends PaintDrop
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public FreshPaintDrop(double rotation, GridObject source)
    {
        super(rotation, false, source);
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
            return super.processDamage((int)(amt*(getDuration()/200.0+1)), source);
        }
    }
}
