package com.karel.game.particles;

/**
 * An explosion. It starts by expanding and then collapsing. 
 * The explosion will explode other obejcts that the explosion intersects.
 * 
 * @author Poul Henriksen
 * @version 1.0.1
 */
public class Explosion extends Wave
{
    
    /**
     * Create a new explosion.
     */
    public Explosion(double size) 
    {
        setImage("explosion-big.png");
        setMaxRadius(60*size);
        setLife(12);
        //Greenfoot.playSound("MetalExplosion.wav");
    }
    public Explosion(){
        this(10);
    }
}