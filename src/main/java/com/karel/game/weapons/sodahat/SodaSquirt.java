package com.karel.game.weapons.sodahat;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.InfectionEffect;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.gridobjects.hitters.Bullet;

public class SodaSquirt extends Bullet {
    private boolean carbonated;
    public SodaSquirt(double dir, GridObject source, boolean heals, boolean isUpgrade){
        super(dir, source);
        setDamage(150);
        setSpeed(17);
        setLife(20);
        if(heals){
            setNumTargets(1);
            setSelfHit(true);
        }else{
            setNumTargets(3);
        }
        carbonated = isUpgrade;
    }
    public void doHit(GridEntity g){
        GridObject b = this;
        if(carbonated)g.applyEffect(new InfectionEffect((ge)->{
            ge.explodeOn(100, (g2)->{
                if(b.isAggroTowards(g2)&&Math.abs(ge.face(g2, false)-ge.getTargetRotation())<30){
                    g2.applyEffect(new PoisonEffect(30, 10, 4, ge));
                }
            }, null);
        }, 40, this));
        if(isAlliedWith(g)){
            heal(g, getDamage());
        }else{
            super.doHit(g);
        }
    }
}
