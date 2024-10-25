import greenfoot.*;
/**
 * Write a description of class Sudo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sudo extends Weapon
{
    private static final int gunReloadTime = 5;
    private int reloadDelayCount;
    private static final int ult = 1;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            ChargeBomb bullet = new ChargeBomb (getHolder().getTargetRotation(), getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            //bullet.move ();
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
            chargeUlt(1);
        }
    }
    public void fireUlt(){
        Nuke bullet = new Nuke(getHolder());
        getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
        Sounds.play("protonwave");
        reloadDelayCount = 0;
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public Sudo(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Minigun";
    }
    public int getRarity(){
        return 0;
    }
}