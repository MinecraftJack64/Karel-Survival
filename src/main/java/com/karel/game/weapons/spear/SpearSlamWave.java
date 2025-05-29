package com.karel.game.weapons.spear;
import com.karel.game.particles.Wave;

/**
 * A proton wave that expands and destroys things in its path.
 * 
 * @author Michael Kolling
 * @version 0.1
 */
public class SpearSlamWave extends Wave
{
    
    public SpearSlamWave()
    {
        setImage("Weapons/spear/wave.png");
        setMaxRadius(80);
        setLife(6);
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
}
