import greenfoot.*;
/**
 * Temporarily immobilizes the target, preventing movement abilities.
 * @author MinecraftJack64
 */
public class ImmobilizeEffect extends DurationEffect
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
    public void onClear(){
        getTarget().mobilize(getID());
    }
    public void onApply(){
        getTarget().immobilize(getID());
    }
}
