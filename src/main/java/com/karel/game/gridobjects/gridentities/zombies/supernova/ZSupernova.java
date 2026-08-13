package com.karel.game.gridobjects.gridentities.zombies.supernova;

import com.karel.game.GridEntity;
import com.karel.game.effects.PullEffect;
import com.karel.game.particles.WaveAttack;

/**
 * A proton wave that expands and destroys things in its path.
 * 
 * @author Michael Kolling
 * @version 0.1
 */
public class ZSupernova extends WaveAttack
{
    private static final int damage = 500;
    
    public ZSupernova(GridEntity source)
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
        g.applyEffect(new PullEffect(face(g, false), 5, 10, this));
        super.doHit(g);
    }
}
