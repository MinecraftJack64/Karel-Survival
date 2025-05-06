package Game;
import greenfoot.*;
/**
 * An effect that "ticks" a certain number of times at an interval, removing itself after.
 * Uses duration as the number of times
 * @author MinecraftJack64
 */
public abstract class TickingEffect extends DurationEffect
{
    private int interval;
    private int tick;
    public TickingEffect(int interval, int duration, GridObject source){
        this(interval, duration, source, new EffectID(source));
    }
    public TickingEffect(int interval, int duration, GridObject source, EffectID id){
        super(duration, source, id);
        setCollisionProtocol(0);
        this.interval = interval;
        tick = 0;
    }
    public TickingEffect(int interval, int duration, EffectID id){
        super(duration, id);
        setCollisionProtocol(0);
        this.interval = interval;
        tick = 0;
    }
    public void setInterval(int it){
        interval = it;
    }
    public int getInterval(){
        return interval;
    }
    public void setDuration(int d){
        super.setDuration(d*interval);
    }
    public int getDuration(){
        return interval*super.getDuration();
    }
    public void affect(){
        tick++;
        if(tick>=interval){
            tick = 0;
            tick();
            super.affect();
        }
    }
    public abstract void tick();
}
