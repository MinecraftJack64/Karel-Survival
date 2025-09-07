package com.karel.game.weapons.hearth;

import com.karel.game.GridEntity;
import com.karel.game.effects.BurnEffect;
import com.karel.game.particles.WaveAttack;

public class HeatWave extends WaveAttack {
    public HeatWave(GridEntity source)
    {
        super(source);
        setImage("Weapons/hearth/proj.png");
        setDamage(0);
        setLife(30);
        setNumTargets(-1);
        setMultiHit(false);
        setAggression(true);
    }
    public void doHit(GridEntity g){
        super.doHit(g);
        if(isAggroTowards(g))g.applyEffect(new BurnEffect(20, 30, 5, this));
    }
}
