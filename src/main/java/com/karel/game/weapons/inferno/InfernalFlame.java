package com.karel.game.weapons.inferno;
import java.util.ArrayList;

import com.karel.game.Bullet;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.BurnEffect;
import com.karel.game.Effect;

/**
 * A single projectile from the Inferno weapon attack. Pierces infinitely but range is reduced after hitting first enemy. Damages and slows on hit, but only if it's the first of its wave of attacks to hit the enemy.
 * 
 * @author Poul Henriksen
 */
public class InfernalFlame extends Bullet
{
    private boolean createTrail;
    public InfernalFlame(double rotation, GridObject source, boolean firstWave)
    {
        super(rotation, source);
        setImage("Weapons/inferno/proj0.png");
        scaleTexture(30);
        setRealRotation(getRealRotation()-180);
        setSpeed(22);
        setLife(15);
        setDamage(55);
        setNumTargets(-1);
        setMultiHit(false);
        createTrail = firstWave;
    }
    public InfernalFlame(double rotation, ArrayList<GridEntity> refhit, GridObject source)
    {
        this(rotation, source, false);
    }
    public void applyPhysics(){
        if(createTrail) addObjectHere(new FireTrail(getSource()));
        super.applyPhysics();
    }
    public Effect getEffect(){
        return new BurnEffect(5, 30, 3, this);
    }
    public void doHit(GridEntity g){
        g.applyEffect(getEffect());
        super.doHit(g);
    }
}
