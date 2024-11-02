import greenfoot.*;
import java.util.List;

/**
 * Write a description of class Pointpinner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pointpinner extends Weapon
{
    private static final int gunReloadTime = 30;
    private int reloadDelayCount;
    private static final int ult = 2000;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            Pin bullet = new Pin(getHolder().getTargetRotation(), getHolder(), this);
            getHolder().addObjectHere(bullet);
            reloadDelayCount = 0;
            //bullet.move ();
            Sounds.play("gunshoot");
        }
    }
    public void fireUlt(){
        Pinpoint bullet = new Pinpoint(getUltUpgrade()==1, getHolder());
        getHolder().getWorld().addObject(bullet, getHolder().getTargetX(), getHolder().getTargetY());
    }
    public void notifyHit(){
        if(getAttackUpgrade()==1)reloadDelayCount+=15;
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public Pointpinner(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Pointpinner";
    }
    public int getRarity(){
        return 0;
    }
}
