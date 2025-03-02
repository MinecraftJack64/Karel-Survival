import greenfoot.*;
/**
 * Temporarily stuns the target, stopping their movement and abilities
 * @author MinecraftJack64
 */
public class StunEffect extends DurationEffect
{
    public StunEffect(int duration, GridObject source){
        super(duration, source);
    }
    public StunEffect(int duration, GridObject source, EffectID id){
        super(duration, source, id);
    }
    public StunEffect(int duration, EffectID id){
        super(duration, id);
    }
    public void onClear(){
        getTarget().unstun(getID());
    }
    public void onApply(){
        getTarget().stun(getID());
    }
}
