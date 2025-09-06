package com.karel.game.weapons.gale;
import java.util.ArrayList;

import com.karel.game.Bullet;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.SpeedPercentageEffect;

/**
 * A single projectile from the Gale weapon attack. Pierces infinitely but range is reduced after hitting first enemy. Damages and slows on hit, but only if it's the first of its wave of attacks to hit the enemy.
 * 
 * @author Poul Henriksen
 */
public class Draft extends Bullet
{
    private boolean hitfirst;
    public Draft(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(22);
        setLife(20);
        setDamage(3);
        setNumTargets(-1);
        setMultiHit(false);
        hitfirst = false;
    }
    public Draft(double rotation, ArrayList<GridEntity> refhit, GridObject source)
    {
        this(rotation, source);
    }
    public void doHit(GridEntity g){
        g.applyEffect(new SpeedPercentageEffect(0.75, 2, this));
        super.doHit(g);
    }
    public void onHit(GridEntity g){
        if(!hitfirst){
            hitfirst = true;
            setSpeed(15);
            setLife(Math.min(getLife(), 15));
        }
    }
    public double damageSecrecy(){
        return super.damageSecrecy()*Math.pow(0.5, getHitStory().size());
    }
}
