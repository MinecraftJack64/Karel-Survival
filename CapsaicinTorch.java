import greenfoot.*;
import java.util.List;

/**
 * Write a description of class CapsaicinTorch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CapsaicinTorch extends Weapon
{
    private int reloadDelayCount;
    private int ammo = 140;
    private int maxAmmo = 140;
    private int ultammo = 0;
    private int range = 0;
    private static final int ult = 2000;
    public void fire(){
        if (ammo>0)
        {
            PepperFlame bullet = new PepperFlame(getHolder().getTargetRotation(), range, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            //bullet.move ();
            Sounds.play("gunshoot");
            ammo--;
            reloadDelayCount = 7;
        }
    }
    public void fireUlt(){
        ultammo+=5;
        setPlayerLockRotation(true);
        setContinueUlt(true);
    }
    public void notifyHit(){
        //
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        if(reloadDelayCount<=0)ammo+=1;
        else reloadDelayCount--;
        if(ammo>maxAmmo){
            ammo = maxAmmo;
        }
        updateAmmo(Math.min(ammo, maxAmmo));
    }
    public CapsaicinTorch(GridObject actor){
        super(actor);
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(maxAmmo, ammo);
    }
    public String getName(){
        return "Capsaicin Torch";
    }
    public int getRarity(){
        return 0;
    }
}
