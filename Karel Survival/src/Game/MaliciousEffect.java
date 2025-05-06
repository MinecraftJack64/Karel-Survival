package Game;
import greenfoot.*;
/**
 * Temporarily immobilizes the target, preventing movement abilities.
 * @author MinecraftJack64
 */
public class MaliciousEffect extends DurationEffect
{
    public MaliciousEffect(int duration, GridObject source){
        super(duration, source);
    }
    public MaliciousEffect(int duration, GridObject source, EffectID id){
        super(duration, source, id);
    }
    public MaliciousEffect(int duration, EffectID id){
        super(duration, id);
    }
    public boolean isMalicious(){
        return true;
    }
}
