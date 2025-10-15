package com.karel.game.gridobjects.gridentities.totems;

import com.karel.game.GridEntity;
import com.karel.game.effects.ReloadPercentageEffect;

public class ReloadTotem extends Totem{
    public void doHit(GridEntity target){
        if(isAlliedWith(target)){
            target.applyEffect(new ReloadPercentageEffect(1.5, 2, this));
        }else if(isAggroTowards(target)){
            target.applyEffect(new ReloadPercentageEffect(0.5, 2, this));
        }
    }
    public String getTotemID(){
        return "reload";
    }
}
