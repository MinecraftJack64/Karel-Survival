package com.karel.game.gridobjects.gridentities.zombies.concentrated;

import com.karel.game.GridEntity;
import com.karel.game.particles.WaveAttack;

/**
 * A proton wave that expands and destroys things in its path.
 * 
 * @author Michael Kolling
 * @version 0.1
 */
public class ZConcentratedWave extends WaveAttack
{
    private static final int damage = 0;
    
    public ZConcentratedWave(GridEntity source)
    {
        super(source);
        setImage("Projectiles/Bullets/zfungalspray.png");
        setDamage(damage);
        setLife(150);
        setNumTargets(-1);
        setMaxRadius(900);
        setMultiHit(false);
    }
    
    public void doHit(GridEntity g){
        damageIgnoreShield(g, (int)Math.min(100, g.getHealth()*0.99));
    }
}
