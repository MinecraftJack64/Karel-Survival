import greenfoot.*;
/**
 * Deal damage over time
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FatalPoisonEffect extends Effect
{
    private int damage, cooldown, interval;
    public FatalPoisonEffect(int damage, int interval, GridObject source){
        this(damage, interval, source, new EffectID(source));
    }
    public FatalPoisonEffect(int damage, int interval, GridObject source, EffectID id){
        super(id);
        setSource(source);
        this.damage = (int)(damage*getSource().getPower());
        cooldown = this.interval = interval;
    }
    public void affect(){
        cooldown--;
        if(cooldown<=0){
            damage(getTarget());
            cooldown = interval;
        }
    }
    public int getCollisionProtocol(){
        return 3;
    }
    public void damage(GridEntity e){
        e.hitIgnoreShield(damage, getSource());
    }
}
