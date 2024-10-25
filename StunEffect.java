import greenfoot.*;
/**
 * Write a description of class StunEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StunEffect extends Effect
{
    int duration;
    GridObject source;
    public StunEffect(int duration, GridObject source){
        this.duration = duration;
        this.source = source;
    }
    public boolean affect(GridEntity e){
        //System.out.println("Poison info: "+nextinterval+" "+remainingtimes);
        duration--;
        e.stun();
        if(duration<=0){
            e.unstun();
            return false;
        }
        return true;
    }
    public void appliedTo(GridEntity source){Sounds.play("stun");}
}
