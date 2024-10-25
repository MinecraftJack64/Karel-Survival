import java.util.List;
import greenfoot.*;
/**
 * Write a description of class AirPump here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AirPump extends Weapon implements LandingHandler
{
    private static final int gunReloadTime = 40;
    private int reloadDelayCount;
    private static final int ult = 225;
    private int chance;
    private boolean nextIsSuper = false;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            double d = Math.min(getHolder().distanceTo(getHolder().getTargetX(), getHolder().getTargetY()), 350);
            WindBurst bullet = new WindBurst(getHolder().getTargetRotation(), d, nextIsSuper, getHolder(), getAttackUpgrade()==1?this:null);
            getHolder().getWorld().addObject (bullet, holder.getRealX(), holder.getRealY());
            reloadDelayCount = 0;
            nextIsSuper = false;
        }
    }
    public void fireUlt(){
        //hop 5 times, dealing damage on landing
        nextIsSuper = true;
        reloadDelayCount = gunReloadTime;
    }
    public void startJump(double rot, double dist, boolean isSuper){
        if(getAttackUpgrade()==1||isSuper){
            setLocked(true);
            double d = dist*(isSuper?7:2);
            getHolder().initiateJump(rot, d, isSuper?300:40);
        }
    }
    public void doLanding(){
        setLocked(false);//allow switching weapons
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(nextIsSuper?gunReloadTime+1:Math.min(reloadDelayCount, gunReloadTime));
    }
    public int getUlt(){
        return ult;
    }
    public AirPump(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;//
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Air Pump";
    }
    public int getRarity(){
        return 4;
    }
}