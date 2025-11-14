package com.karel.game.weapons.trolltest;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.DurationEffect;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.effects.TeamSwitchEffect;
import com.karel.game.gridobjects.hitters.Bullet;

public class TrollRiddle extends Bullet{
    private int choice;
    public TrollRiddle(double rot, int choice, GridObject source){
        super(rot, source);
        setDamage(50+choice*50);
        this.choice = choice;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        targ.applyEffect(choice==0?new SpeedPercentageEffect(0.5, 30, this):(choice==1?new SpeedPercentageEffect(-0.5, 30, this):new TeamSwitchEffect(getTeam(), 30, this)));
        targ.applyEffect(new DurationEffect(100, this, new EffectID("trolltest-riddle")).setCollisionProtocol(2));
    }
    public double damageSecrecy(){
        return super.damageSecrecy()*6.67;
    }
}
