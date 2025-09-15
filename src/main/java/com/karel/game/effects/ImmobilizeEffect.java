package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * Temporarily immobilizes the target, preventing movement abilities. Attacks and other non-movement abilities are unaffected.
 * ID "immobilize"
 * @author MinecraftJack64
 */
public class ImmobilizeEffect extends MaliciousEffect
{
    public ImmobilizeEffect(int duration, GridObject source){
        super(duration, source);
    }
    public ImmobilizeEffect(int duration, GridObject source, EffectID id){
        super(duration, source, id);
    }
    public ImmobilizeEffect(int duration, EffectID id){
        super(duration, id);
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/immobilize.png";
    }
    public void onClear(){
        getTarget().mobilize(getID());
    }
    public void onApply(){
        getTarget().immobilize(getID());
    }
}
