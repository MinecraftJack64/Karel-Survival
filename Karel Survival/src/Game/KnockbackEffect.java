package Game;
import greenfoot.*;
/**
 * Write a description of class KnockbackEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class KnockbackEffect extends StunEffect
{
    private double rot, d, h;
    public KnockbackEffect(double r, double dist, double h, GridObject source){
        super(1, source);//don't know how long the jump is
        rot = r;
        d = dist;
        this.h = h;
    }
    public void onApply(){
        GridEntity source = getTarget();
        if(source.canBePulled()){
            source.initiateJump(rot, d, h);
            setDuration((int)(source.getPhysicsArc().getDuration()));
            super.onApply();
        }else{
            clear();
        }
    }
    public boolean isMalicious(){
        return false;
    }
}
