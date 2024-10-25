import greenfoot.*;
/**
 * Write a description of class SilenceEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SilenceEffect extends Effect
{
    int duration;
    GridObject source;
    public SilenceEffect(int duration, GridObject source){
        this.duration = duration;
        this.source = source;
    }
    public boolean affect(GridEntity e){
        //System.out.println("Poison info: "+nextinterval+" "+remainingtimes);
        duration--;
        e.mute();
        if(duration<=0){
            e.unmute();
            return false;
        }
        return true;
    }
    public void appliedTo(GridEntity source){Sounds.play("stun");}
}
