import greenfoot.*;
/**
 * Write a description of class JadeCutter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JadeCutter extends Weapon
{
    private static final int ult = 1100;
    private StaticJadeBlade lasso;
    public void fire(){//one full ammo deals 350 damage
        if (lasso.canAttack()) 
        {
            lasso.fire(getHolder().getTargetRotation());
            lasso = null;
        }
    }
    public void fireUlt(){
        FlyingCircSaw bullet = new FlyingCircSaw(getHolder().getTargetRotation(), getHolder());
        getHolder().addObjectHere(bullet);
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        if(lasso==null){
            lasso = new StaticJadeBlade(getHolder());
        }
        //updateAmmo(Math.min(reloadtime-attackcooldown, reloadtime));
    }
    public JadeCutter(GridObject actor){
        super(actor);
    }
    public void equip(){
        super.equip();
        //getHolder().getWorld().gameUI().newAmmo(reloadtime, reloadtime-attackcooldown);
    }
    public String getName(){
        return "Jade Cutter";
    }
    public int getRarity(){
        return 3;
    }
}
