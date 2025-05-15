package com.karel.game;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class WaterBalloon extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 100;
    private boolean isSuper;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    //private double targrot;
    
    public WaterBalloon(double rotation, GridObject source, boolean isSuper)
    {
        super(rotation, source);
        setSpeed(25);
        setLife(15);
        setDamage(115);
        this.isSuper = isSuper;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        for(int i = -35; i <= 35; i+=10){
            WaterSplash w = new WaterSplash(i+getDirection(), getHitStory(), this);
            addObjectHere(w);
        }
        if(isSuper){
            for(int i = -30; i <= 30; i+=20){
                SuperWaterSplash w = new SuperWaterSplash(i+getDirection(), getHitStory(), this);
                addObjectHere(w);
            }
        }
    }
    public void expire(){
        for(int i = -35; i <= 35; i+=10){
            WaterSplash w = new WaterSplash(i+getDirection(), getHitStory(), this);
            addObjectHere(w);
        }
        if(isSuper){
            for(int i = -30; i <= 30; i+=20){
                SuperWaterSplash w = new SuperWaterSplash(i+getDirection(), getHitStory(), this);
                addObjectHere(w);
            }
        }
        super.expire();
    }
}
