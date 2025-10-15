package com.karel.game.weapons.traps;

import com.karel.game.GridEntity;
import com.karel.game.effects.KnockbackEffect;

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
        setImage("Weapons/traps/projUltUpgrade.png");
    }
    
    public void snap(GridEntity asteroid){
        super.snap(asteroid);
        explodeOnEnemies(100, (g)->{
            if(g!=asteroid){
                g.applyEffect(new KnockbackEffect(face(g, false), 100, 30, this));
            }
        });
    }
    public String getPetID(){
        return "traps-beartrap+";
    }
}
