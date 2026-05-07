package com.karel.game.weapons.javabean;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.PoisonEffect;

public class PoisonousJavaBean extends JavaBean {
    public PoisonousJavaBean(double rotation, GridObject source)
    {
        super(rotation, source);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(getLife()>5)targ.applyEffect(new PoisonEffect(10, 10, 5, this));
    }
}
