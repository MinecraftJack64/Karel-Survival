package com.karel.game.particles;

public class FlameTrail extends Explosion
{
    /**
     * Create a new flame trail.
     */
    public FlameTrail(double size) 
    {
        setImage("Weapons/inferno/proj0.png");
        setMaxRadius(10 * size);
        setLife(12);
    }

    public FlameTrail() {
        this(10);
    }
    
}
