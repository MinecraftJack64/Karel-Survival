package com.karel.game.weapons.inferno;
import java.util.ArrayList;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.HealCharge;
import com.karel.game.Effect;

/**
 * A single projectile from the Inferno weapon attack. Pierces infinitely but range is reduced after hitting first enemy. Damages and slows on hit, but only if it's the first of its wave of attacks to hit the enemy.
 * 
 * @author Poul Henriksen
 */
public class SoulInfernalFlame extends InfernalFlame
{
    public SoulInfernalFlame(double rotation, GridObject source, boolean firstWave)
    {
        super(rotation, source, firstWave);
        setSpeed(22);
        setLife(15);
        setDamage(55);
        setNumTargets(-1);
        setMultiHit(false);
    }
    public SoulInfernalFlame(double rotation, ArrayList<GridEntity> refhit, GridObject source)
    {
        this(rotation, source, false);
    }
    public Effect getEffect(){
        return new SoulBurnEffect(5, 30, 3, this);
    }
    public void doHit(GridEntity g){
        super.doHit(g);
        if(g.isDead()&&getLife()>10){
            addObjectHere(new HealCharge(getRealRotation(), getSource().getParentAffecter(), getDamage()));
        }
    }
}
