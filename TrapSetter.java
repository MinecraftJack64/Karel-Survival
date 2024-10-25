/**
 * Write a description of class TrapSetter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TrapSetter extends Weapon
{
    private static final int gunReloadTime = 50;
    private int reloadDelayCount;
    private static final int ult = 700;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            Mousetrap bullet = getAttackUpgrade()==1?new Mousetrap(getHolder().getTargetRotation(), getHolder()):new Mousetrap(getHolder());
            //WeaponFrag bullet = new WeaponFrag();
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            Sounds.play("setuptrap");
            reloadDelayCount = 0;
        }
    }
    public void fireUlt(){
        setTrap(100, 100);
        setTrap(-100, -100);
        setTrap(-100, 100);
        setTrap(100, -100);
    }
    public void setTrap(double offsetx, double offsety){
        BearTrap bullet = new BearTrap(getHolder());
        //WeaponFrag bullet = new WeaponFrag();
        getHolder().getWorld().addObject (bullet, getHolder().getRealX()+offsetx, getHolder().getRealY()+offsety);
        Sounds.play("setuptrap");
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public int getUlt(){
        return ult;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public TrapSetter(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public String getName(){
        return "Mouse Traps";
    }
    public int getRarity(){
        return 0;
    }
}
