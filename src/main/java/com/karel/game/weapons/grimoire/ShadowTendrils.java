package com.karel.game.weapons.grimoire;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.StunEffect;
import com.karel.game.gridobjects.hitters.Hitter;

public class ShadowTendrils extends Hitter {
    private int life = 45;
    private int startCooldown = 10;
    GridEntity t;
    public ShadowTendrils(GridEntity t, GridObject source){
        super(source);
        this.t = t;
        setDamage(0);
        setRange(50);
        setNumTargets(1);
        setImage("anchor.png");
    }
    public void update(){
        if(startCooldown>0){
            startCooldown--;
            if(startCooldown==0){
                checkHit();
            }
        }
        super.update();
        life--;
        if(life<=0){
            die();
            getWorld().removeObject(this);
        }
    }
    public boolean isPotentialTarget(GridEntity g){
        return g==t;
    }
    public void doHit(GridEntity t){
        t.applyEffect(new StunEffect(30, this));
        super.doHit(t);
    }
}
