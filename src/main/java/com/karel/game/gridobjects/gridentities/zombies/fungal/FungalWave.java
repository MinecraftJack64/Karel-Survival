package com.karel.game.gridobjects.gridentities.zombies.fungal;

import com.karel.game.GridEntity;
import com.karel.game.particles.WaveAttack;

/**
 * A proton wave that expands and destroys things in its path.
 * 
 * @author Michael Kolling
 * @version 0.1
 */
public class FungalWave extends WaveAttack
{
    private static final int damage = 50;
    
    public FungalWave(FungalZombie source)
    {
        super(source);
        setImage("Projectiles/Bullets/zfungalspray.png");
        setDamage(damage);
        setLife(30);
        setNumTargets(-1);
        setMultiHit(false);
        setAggression(true);
    }
    
    public void doHit(GridEntity g){
        super.doHit(g);
        ((FungalZombie)getSource()).notifyHit(g);
    }
}
