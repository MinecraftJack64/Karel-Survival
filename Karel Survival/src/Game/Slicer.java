package Game;
import greenfoot.*;
/**
 * Write a description of class Slicer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Slicer extends Weapon
{
    private static final int ult = 2100, reloadtime = 15;
    private CircSaw lasso;
    private int ultchargecooldown = 0;
    private int attackcooldown = 0;
    public void fire(){//one full ammo deals 350 damage
        if (attackcooldown<=0) 
        {
            CircSaw bullet = new CircSaw(getHand().getTargetRotation(), getHolder());
            getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
            //bullet.move ();
            lasso = bullet;
            attackcooldown = reloadtime;
            Sounds.play("lifestealshoot");
        }
    }
    public void fireUlt(){
        FlyingCircSaw bullet = new FlyingCircSaw(getHand().getTargetRotation(), getHolder());
        getHolder().addObjectHere(bullet);
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        if(lasso!=null&&lasso.hasReturned()){
            lasso = null;
        }
        if(lasso==null){
            attackcooldown--;
        }
        updateAmmo(Math.min(reloadtime-attackcooldown, reloadtime));
    }
    public Slicer(ItemHolder actor){
        super(actor);
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(reloadtime, reloadtime-attackcooldown);
    }
    public String getName(){
        return "Slicer";
    }
    public int getRarity(){
        return 2;
    }
}




