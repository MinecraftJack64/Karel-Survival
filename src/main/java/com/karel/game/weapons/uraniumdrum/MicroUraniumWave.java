package com.karel.game.weapons.uraniumdrum;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.particles.WaveAttack;

/**
 * A proton wave that expands and destroys things in its path.
 * 
 * @author Michael Kolling
 * @version 0.1
 */
public class MicroUraniumWave extends WaveAttack
{
    private static final int damage = 50;
    
    public MicroUraniumWave(GridObject source)
    {
        super(source);
        setImage("Projectiles/Bullets/zfungalspray.png");
        setDamage(damage);
        setMaxRadius(160);
    }
    public void doHit(GridEntity targ){
        targ.applyEffect(new PoisonEffect(25, 30, 4, this));
        super.doHit(targ);
    }
}
