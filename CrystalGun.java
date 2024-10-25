import greenfoot.*;
/**
 * Write a description of class CrystalGun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CrystalGun extends Weapon
{
    private static final int gunReloadTime = 30;
    private int reloadDelayCount;
    private static final int ult = 1000;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            for(int deg = 45; deg<=315; deg+=90){
                Shard mbullet = new Shard(deg, holder);
                getHolder().addObjectHere(mbullet);
            }
            reloadDelayCount = 0;
        }
    }
    public void fireUlt(){
        for(int deg = 0; deg<=270; deg+=90){
            Crystallizer mbullet = new Crystallizer(deg, holder);
            getHolder().addObjectHere(mbullet);
        }
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public CrystalGun(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Crystal Gun";
    }
    public int getRarity(){
        return 2;
    }
}