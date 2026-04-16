package com.karel.game.weapons.morphingstone;

import com.karel.game.Boomerang;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.DamageExposureEffect;
import com.karel.game.effects.PowerPercentageEffect;
import com.karel.game.effects.SpeedPercentageEffect;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class EssenceStone extends Boomerang
{
    GridEntity hypnoed;
    MorphingStone necromancer;
    
    public EssenceStone(double rotation, GridObject source, MorphingStone necro)
    {
        super(rotation, source);
        setSpeed(18);
        setLife(20);
        setDamage(200);
        necromancer = necro;
        setNumTargets(1);
        setReturnSpeed(20);
        setExpireReturn(2);
    }
    public void dieForReal(){
        if(necromancer!=null)necromancer.notifyHit();
        super.dieForReal();
    }
    public void doHit(GridEntity targ){
        targ.applyEffect(new SpeedPercentageEffect(0.8, 60, this));
        targ.applyEffect(new DamageExposureEffect(1.2, 60, this));
        targ.applyEffect(new PowerPercentageEffect(0.8, 60, this));
        super.doHit(targ);
        if(targ.isDead()){
            if(necromancer!=null){
                necromancer.notifyKill(targ);
            }
        }
    }
    public double damageSecrecy(){
        return super.damageSecrecy()*0.25;
    }
}
