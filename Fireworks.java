import greenfoot.*;
/**
 * Write a description of class Fireworks here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fireworks extends Weapon
{
    private static final int gunReloadTime = 30;
    private static final int ammoReloadTime = 60;
    private static final int maxAmmo = 4;
    private int reloadDelayCount;
    private int ammo;
    private int ammoReloadDelay;
    private static final int ult = 2000;
    private boolean nextammosupercharged = false;
    public void fire(){//one full ammo deals 350 damage
        if (reloadDelayCount >= gunReloadTime&&ammo>0) 
        {
            if(nextammosupercharged){
                //
            }
            double d = Math.min(2*getHolder().distanceTo(getHolder().getTargetX(), getHolder().getTargetY()), 800);
            FireworkRocket bullet = new FireworkRocket (getHolder().getTargetRotation(), d, 400, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            //bullet.move ();
            Sounds.play("fireworkshoot");
            reloadDelayCount = 0;
            ammo--;
            nextammosupercharged = false;
        }
    }
    public void fireUlt(){
        Nuke bullet = new Nuke(getHolder());
        getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
        //bullet.move ();
        Sounds.play("lassoshoot");
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            if(ammo<maxAmmo){
                ammoReloadDelay++;
                if(ammoReloadDelay>=ammoReloadTime){
                    ammo++;
                    ammoReloadDelay = 0;
                }
            }
        }
        if(nextammosupercharged){
            updateAmmo(getAmmoBar().getMax()+1);
        }else updateAmmo(ammo*ammoReloadTime+ammoReloadDelay);
    }
    public Fireworks(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammoReloadDelay = 0;
        ammo = 1;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(maxAmmo*ammoReloadTime, ammo*ammoReloadTime+ammoReloadDelay, 4);
    }
    public String getName(){
        return "Fireworks";
    }
    public int getRarity(){
        return 3;
    }
}
