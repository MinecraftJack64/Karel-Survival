package com.karel.game.gridobjects.gridentities.zombies;

public class Boss extends Zombie{
    public Boss(){
        scaleTexture(90, 90);
        addEffectImmunities("team_switch", "fatal_poison", "sizeMal");
    }
    public int getPhase(){
        return 1;
    }
    public boolean acceptExternalShields(){
        return false;
    }
}