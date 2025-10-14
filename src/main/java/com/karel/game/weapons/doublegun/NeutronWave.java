package com.karel.game.weapons.doublegun;

import com.karel.game.effects.ReloadPercentageEffect;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.particles.WaveAttack;

/**
 * A proton wave that expands and destroys things in its path.
 * 
 * @author Michael Kolling
 * @version 0.1
 */
public class NeutronWave extends WaveAttack
{
    private static final int damage = 400;
    private int radiate;
    
    public NeutronWave(GridObject source, int radiate)
    {
        super(source);
        setImage("Weapons/doublegun/projUlt.png");
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
    }
    
    public void doHit(GridEntity g){
        super.doHit(g);
        if(radiate == 1){
            g.applyEffect(new SpeedPercentageEffect(0.8, -1, this));
        }else if(radiate == 2){
            g.applyEffect(new ReloadPercentageEffect(0.5, 150, this));
        }
    }
    public boolean covertDamage(){
        return true;
    }
}
