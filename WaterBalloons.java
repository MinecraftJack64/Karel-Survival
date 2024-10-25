import greenfoot.*;
/**
 * Write a description of class WaterBalloons here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaterBalloons extends Weapon
{
    private static final int gunReloadTime = 15;
    private static final int ammoReloadTime = 35;
    private static final int maxAmmo = 2;
    private int reloadDelayCount;
    private int ammo;
    private int ammoReloadDelay;
    private static final int ult = 800;
    public void fire(){//one full ammo deals 350 damage
        if (reloadDelayCount >= gunReloadTime&&ammo>0) 
        {
            WaterBalloon wb = new WaterBalloon(getHolder().getTargetRotation(), getHolder());
            getHolder().addObjectHere(wb);
            //bullet.move ();
            Sounds.play("fireworkshoot");
            reloadDelayCount = 0;
            ammo--;
        }
    }
    public void fireUlt(){
        Sounds.play("crossbowshoot");
        double x = getHolder().getTargetX();
        double y = getHolder().getTargetY();
        double d = Math.min(600, getHolder().distanceTo(x, y));
        BigWaterBalloon bullet = new BigWaterBalloon(getHolder().getTargetRotation(), d, 200, getHolder());
        getHolder().addObjectHere(bullet);
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
        updateAmmo(ammo*ammoReloadTime+ammoReloadDelay);
    }
    public WaterBalloons(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammoReloadDelay = 0;
        ammo = 1;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(maxAmmo*ammoReloadTime, ammo*ammoReloadTime+ammoReloadDelay, 2);
    }
    public String getName(){
        return "Water Balloons";
    }
    public int getRarity(){
        return 1;
    }
}
