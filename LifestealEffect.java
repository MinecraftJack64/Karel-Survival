import greenfoot.*;
/**
 * Write a description of class LifestealEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LifestealEffect extends Effect
{
    int damage, interval, nextinterval, remainingtimes;
    GridEntity source;
    public LifestealEffect(int damage, int interval, int times, GridEntity source){
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
            e.hit(damage, source);
            if(!source.isDead()){
                source.heal(source, damage);
            }
            remainingtimes--;
            if(remainingtimes==0)
                return false;
        }
        return true;
    }
}
