import greenfoot.*;
/**
 * Write a description of class WaterBalloons here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaterBalloons extends Weapon implements AmmoHolder
{
    private static final int gunReloadTime = 15;
    private int reloadDelayCount;
    private static final int ult = 800;
    AmmoManager ammo;
    public void fire(){//one full ammo deals 350 damage
        if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            WaterBalloon wb = new WaterBalloon(getHolder().getTargetRotation(), getHolder());
            getHolder().addObjectHere(wb);
            //bullet.move ();
            Sounds.play("fireworkshoot");
            reloadDelayCount = 0;
            ammo.useAmmo();
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
            ammo.reload();
        }
        updateAmmo(ammo.getAmmoBar());
    }
    public WaterBalloons(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new AmmoManager(35, 1, 2);
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(ammo.getMaxAmmoBar(), ammo.getAmmoBar(), ammo.getMaxAmmo());
    }
    public String getName(){
        return "Water Balloons";
    }
    public int getRarity(){
        return 1;
    }
}
