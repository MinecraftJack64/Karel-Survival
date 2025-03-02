import greenfoot.*;
/**
 * Temporarily mutes the target, preventing abilities like attacks.
 * @author MinecraftJack64
 */
public class SilenceEffect extends DurationEffect
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
    public void onClear(){
        getTarget().unmute(getID());
    }
    public void onApply(){
        getTarget().mute(getID());
    }
}
