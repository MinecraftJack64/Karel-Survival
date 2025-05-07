package com.karel.game;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class UpgradedBearTrap extends BearTrap
{
    public UpgradedBearTrap(double rot, GridEntity source)
    {
        super(rot, source);
    }
    
    public void snap(GridEntity asteroid){
        super.snap(asteroid);
        explodeOnEnemies(100, (g)->{
            if(g!=asteroid){
                g.applyEffect(new KnockbackEffect(face(g, false), 100, 30, this));
            }
        });
    }
}
