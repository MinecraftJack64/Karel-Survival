package com.karel.game.effects;
/**
 * A combination of damage and effects to apply at once.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EffectPackage  
{
    // instance variables - replace the example below with your own
    private int damage;
    private Effect[] effects;
    public EffectPackage(int dmg, Effect... effects){
        damage = dmg;
        this.effects = effects;
    }
    public void applyTo(com.karel.game.GridEntity e){
        e.hit(damage, null);
        for(Effect ef : effects){
            e.applyEffect(ef);
        }
    }
}
