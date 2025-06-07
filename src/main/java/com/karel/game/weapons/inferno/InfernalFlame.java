package com.karel.game.weapons.inferno;
import java.util.ArrayList;

import com.karel.game.Bullet;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.PoisonEffect;

/**
 * A single projectile from the Inferno weapon attack. Pierces infinitely but range is reduced after hitting first enemy. Damages and slows on hit, but only if it's the first of its wave of attacks to hit the enemy.
 * 
 * @author Poul Henriksen
 */
public class InfernalFlame extends Bullet
{
    public InfernalFlame(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(22);
        setLife(15);
        setDamage(55);
        setNumTargets(-1);
        setMultiHit(false);
    }
    public InfernalFlame(double rotation, ArrayList<GridEntity> refhit, GridObject source)
    {
        this(rotation, source);
    }
    public void doHit(GridEntity g){
        g.applyEffect(new PoisonEffect(5, 30, 3, this));
        super.doHit(g);
    }
}
