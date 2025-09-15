package com.karel.game.effects;

import com.karel.game.GridObject;

/**
 * Temporarily mutes the target, preventing abilities like attacks. Movement is unaffected.
 * ID "silence"
 * @author MinecraftJack64
 */
public class SilenceEffect extends MaliciousEffect
{
    public SilenceEffect(int duration, GridObject source){
        super(duration, source);
    }
    public SilenceEffect(int duration, GridObject source, EffectID id){
        super(duration, source, id);
    }
    public SilenceEffect(int duration, EffectID id){
        super(duration, id);
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/silence.png";
    }
    public void onClear(){
        getTarget().unmute(getID());
    }
    public void onApply(){
        getTarget().mute(getID());
    }
}
