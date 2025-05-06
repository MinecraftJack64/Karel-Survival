package Game;
/**
 * Write a description of class RockCatapult here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RockCatapult extends Weapon
{
    private static final int gunReloadTime = 70;
    private int reloadDelayCount;
    private static final int ult = 800;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 400);
            FlyingRock bullet = new FlyingRock (getHand().getTargetRotation(), d, d/2, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            Sounds.play("airtoss");
            //d400=reload70, d0=reload20
            reloadDelayCount = getAttackUpgrade()==1?(50-(int)(d/8)):0;
        }
    }
    public void fireUlt(){
        Asteroid bullet = new Asteroid(getHand().getTargetRotation(), getHolder());
        getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public int getUlt(){
        return ult;
    }
    public RockCatapult(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Rock Launcher";
    }
    public int getRarity(){
        return 1;
    }
}






