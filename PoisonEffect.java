import greenfoot.*;
/**
 * Write a description of class PoisonEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PoisonEffect extends Effect
{
    int damage, interval, nextinterval, remainingtimes;
    GridObject source;
    public PoisonEffect(int damage, int interval, int times, GridObject source){
        this.damage = (int)(damage*source.getPower());
        this.interval = interval;
        this.nextinterval = interval;
        this.remainingtimes = times;
        this.source = source;
    }
    public boolean affect(GridEntity e){
        //System.out.println("Poison info: "+nextinterval+" "+remainingtimes);
        nextinterval--;
        if(nextinterval<=0){
            nextinterval = interval;
            damage(e);
            remainingtimes--;
            if(remainingtimes==0)
                return false;
        }
        return true;
    }
    public void damage(GridEntity e){
        e.hit(damage, source);
    }
}
