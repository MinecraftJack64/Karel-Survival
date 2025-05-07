package com.karel.game;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class JadeBlade extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    private int size = 80;
    private int sizeReduction = 8;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public JadeBlade(double rotation, int size, boolean isUpgrade, GridObject source)
    {
        super(rotation, source);
        setSpeed(12);
        setLife(80);
        setNumTargets(-1);
        this.size = size;
        sizeReduction = isUpgrade?5:10;
        getImage().scale(size, size);
        setDamage(size*5);
        setMultiHit(false);
    }
    public void animate(){
        setRealRotation(getRealRotation()+30);
    }
    public void applyPhysics(){
        if(size<=0){
            die();
            return;
        }
        getImage().scale(size, size);
        setDamage(size*5);
        super.applyPhysics();
        size-=2;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        size-=8;
    }
}
