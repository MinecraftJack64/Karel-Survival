package com.karel.game;
import java.util.ArrayList;

/**
 * A single projectile from the Inferno weapon attack. Pierces infinitely but range is reduced after hitting first enemy. Damages and slows on hit, but only if it's the first of its wave of attacks to hit the enemy.
 * 
 * @author Poul Henriksen
 */
public class InfernalFlame extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private boolean hitfirst;
    //private int life = 10;
    private ArrayList<GridEntity> hitstory2;
    public InfernalFlame(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(22);
        setLife(15);
        setDamage(55);
        setNumTargets(-1);
        setMultiHit(false);
        hitfirst = false;
    }
    public InfernalFlame(double rotation, ArrayList<GridEntity> refhit, GridObject source)
    {
        this(rotation, source);
        hitstory2 = refhit;
    }
    public void doHit(GridEntity g){
        g.applyEffect(new PoisonEffect(5, 30, 3, this));
        super.doHit(g);
    }
}
