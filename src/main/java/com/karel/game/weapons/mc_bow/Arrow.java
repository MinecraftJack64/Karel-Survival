package com.karel.game.weapons.mc_bow;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.BurnEffect;
import com.karel.game.gridobjects.hitters.FlyingProjectile;

public class Arrow extends FlyingProjectile{
    private int atkUp;
    public Arrow(double rotation, double targetdistance, double height, GridObject source, int au){
        super(rotation, targetdistance, height, source);
        setRange(30);
        setDamage(au==1?250:200);
        this.atkUp = au;
    }
    public void doHit(GridEntity ge){
        if(atkUp==2){
            ge.applyEffect(new BurnEffect(50, 30, 3, this));
        }else if(atkUp==1){
            ge.knockBack(50, 50, 30, this);
        }
        super.doHit(ge);
    }
}
