package Game;
import greenfoot.*;
/**
 * Write a description of class FlashDrive here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FlashDrive extends Weapon
{
    private static final int ult = 1900;
    private static final int reloadTime = 10;
    private int ammo;
    public void fire(){
        if(ammo>=reloadTime){
            FlashBolt bullet2 = new FlashBolt(getHand().getTargetRotation()+15, getHolder());
            getHolder().getWorld().addObject (bullet2, getHolder().getRealX(), getHolder().getRealY());
            FlashBolt bullet3 = new FlashBolt(getHand().getTargetRotation()-15, getHolder());
            getHolder().getWorld().addObject (bullet3, getHolder().getRealX(), getHolder().getRealY());
            bullet2.setOther(bullet3);
            bullet3.setOther(bullet2);
            ammo = 0;
            Sounds.play("gunshoot");
        }
    }
    public void fireUlt(){
        double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 200);
        ICDropper bullet = new ICDropper(getHand().getTargetRotation(), d, d, getHolder());
        getHolder().addObjectHere(bullet);
        Sounds.play("protonwave");
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        if(ammo<reloadTime)ammo++;
        updateAmmo(ammo);
    }
    public FlashDrive(ItemHolder actor){
        super(actor);
        ammo = reloadTime;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(reloadTime, ammo);
    }
    public String getName(){
        return "Flash Drive";
    }
    public int getRarity(){
        return 4;
    }
}






