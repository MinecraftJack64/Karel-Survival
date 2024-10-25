import greenfoot.*;
/**
 * Write a description of class Blade here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Blade extends Weapon
{
    private static final int gunReloadTime = 30;
    private int reloadDelayCount;
    private static final int ult = 800;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            Sword bullet = new Sword(getHolder().getTargetRotation(), 40, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            //bullet.move ();
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
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
    public Blade(GridObject actor){
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
