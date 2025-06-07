package com.karel.game.weapons.gun;

import com.karel.game.FatalPoisonEffect;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.particles.WaveAttack;

/**
 * A proton wave that expands and destroys things in its path.
 * 
 * @author Michael Kolling
 * @version 0.1
 */
public class ProtonWave extends WaveAttack
{
    private static final int damage = 600;
    private boolean radiate;
    
    public ProtonWave(GridObject source, boolean radiate)
    {
        super(source);
        setImage("Weapons/gun/projUlt.png");
        setDamage(damage);
        setNumTargets(-1);
        setMultiHit(false);
        setAggression(true);
        this.radiate = radiate;
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void applyPhysics()
    {
        setDamage(getDamage() - 10);
        super.applyPhysics();
        System.out.println("ProtonWave range"+getRange());
    }
    
    public void doHit(GridEntity g){
        super.doHit(g);
        if(radiate){
            g.applyEffect(new FatalPoisonEffect(5, 3, this));
        }
    }
    public boolean covertDamage(){
        return true;
    }
}
