package com.karel.game.gridobjects.gridentities.zombies.shaman;

import com.karel.game.GridEntity;
import com.karel.game.particles.WaveAttack;

/**
 * A proton wave that expands and destroys things in its path.
 * 
 * @author Michael Kolling
 * @version 0.1
 */
public class ShamanWave extends WaveAttack
{
    private static final int damage = 0;
    
    public ShamanWave(ShamanZombie source)
    {
        super(source);
        setImage("Projectiles/Bullets/zfungalspray.png");
        setDamage(damage);
        setLife(30);
        setNumTargets(6);
        setMaxRadius(200);
        setMultiHit(false);
        setAggression(false);
        setHitAllies(true);
        setSelfHarm(true);
    }
    
    public void doHit(GridEntity g){
        super.doHit(g);
        if(g.getHealth()==g.getMaxHealth())return;
        int amt = (int)((g.getMaxHealth()-g.getHealth())*0.35);
        heal(g, amt);
        g.addObjectHere(new EvilSpiritZombie((GridEntity)getSource(), amt));
    }
}
