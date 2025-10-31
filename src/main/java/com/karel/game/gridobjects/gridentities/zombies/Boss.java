package com.karel.game.gridobjects.gridentities.zombies;

import com.karel.game.effects.FatalPoisonEffect;
import com.karel.game.effects.SizePercentageEffect;
import com.karel.game.effects.TeamSwitchEffect;

public class Boss extends Zombie{
    public Boss(){
        scaleTexture(90, 90);
        addEffectImmunities(TeamSwitchEffect.class, FatalPoisonEffect.class, SizePercentageEffect.class); // TODO: only malicious size
    }
    public int getPhase(){
        return 1;
    }
}