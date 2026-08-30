package com.karel.game.weapons.uraniumdrum;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.effects.PowerPercentageEffect;
import com.karel.game.effects.SilenceEffect;
import com.karel.game.gridobjects.WaveAttack;

/**
 * A proton wave that expands and destroys things in its path.
 * 
 * @author Michael Kolling
 * @version 0.1
 */
public class UraniumWave extends WaveAttack
{
    private static final int damage = 100;
    private boolean upgrade;
    
    public UraniumWave(boolean upgrade, GridObject source)
    {
        super(source);
        this.upgrade = upgrade;
        setImage("Projectiles/Bullets/zfungalspray.png");
        setDamage(damage);
        setMaxRadius(160);
    }
    public void doHit(GridEntity targ){
        targ.applyEffect(new PoisonEffect(50, 30, 4, this));
        if(upgrade){
            targ.applyEffect(new SilenceEffect(20, this));
            targ.applyEffect(new PowerPercentageEffect(0.5, 60, this));
        }
        super.doHit(targ);
    }
}
