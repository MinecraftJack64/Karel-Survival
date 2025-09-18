package com.karel.game;

import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class JadeBlade extends Bullet
{
    private int size = 80;
    private int sizeReduction = 8;
    
    public JadeBlade(double rotation, int size, boolean isUpgrade, GridObject source)
    {
        super(rotation, source);
        setSpeed(12);
        setLife(80);
        setNumTargets(-1);
        this.size = size;
        sizeReduction = isUpgrade?5:10;
        setDamage(size*5);
        setMultiHit(false);
    }
    public void animate(){
        setRotation(getRotation()+30);
        scaleTexture(size, size);
    }
    public void applyPhysics(){
        if(size<=0){
            die();
            return;
        }
        setDamage(size*5);
        super.applyPhysics();
        size-=2;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        size-=8;
    }
}
