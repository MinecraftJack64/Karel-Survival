package com.karel.game.gridobjects.gridentities.zombies.cancer;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

public class ZMetastasis extends ZBullet{
    CancerZombie source;
    double chance;
    public ZMetastasis(double dir, CancerZombie cz, double chance){
        super(dir, cz);
        source = cz;
        this.chance = chance;
        setDamage(10);
        setSpeed(15);
        setLife(25);
    }
    public void doHit(GridEntity g){
        g.applyEffect(new PoisonEffect(10, 30, 2, this));
        super.doHit(g);
        if(Greenfoot.getRandomNumber()<chance){
            source.notifyHit();
        }
    }
}
