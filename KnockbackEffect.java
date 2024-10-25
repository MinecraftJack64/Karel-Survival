import greenfoot.*;
/**
 * Write a description of class KnockbackEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class KnockbackEffect extends Effect
{
    int duration;
    double rot, d, h;
    GridObject source;
    public KnockbackEffect(double r, double dist, double h, GridObject source){
        this.duration = 1;
        rot = r;
        d = dist;
        this.h = h;
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
    public void appliedTo(GridEntity source){
        if(source.canBePulled()){
            source.initiateJump(rot, d, h);
            duration = (int)(source.getPhysicsArc().getDuration());
        }else{
            duration = 0;
        }
    }
}
