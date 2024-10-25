import greenfoot.*;
/**
 * Write a description of class InvisibilityEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InvisibilityEffect extends Effect
{
    int duration;
    GridObject source;
    public InvisibilityEffect(int duration, GridObject source){
        this.duration = duration;
        this.source = source;
    }
    public boolean affect(GridEntity e){
        //System.out.println("Poison info: "+nextinterval+" "+remainingtimes);
        duration--;
        e.setDetectable(false);
        if(duration<=0){
            e.setDetectable(true);
            return false;
        }
        return true;
    }
    public void appliedTo(GridEntity source){Sounds.play("stun");}
}
