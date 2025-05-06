package Game;
import greenfoot.*;
/**
 * Deal damage over time
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealEffect extends TickingEffect
{
    private int damage;
    public HealEffect(int damage, int interval, int times, GridObject source){
        this(damage, interval, times, source, new EffectID(source));
    }
    public HealEffect(int damage, int interval, int times, GridObject source, EffectID id){
        super(interval, times, source, id);
        this.damage = (int)(damage*getSource().getPower());
    }
    public void tick(){
        heal(getTarget());
    }
    public void heal(GridEntity e){
        e.heal(damage, getSource());
    }
}
