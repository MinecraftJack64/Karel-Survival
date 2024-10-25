import greenfoot.*;
/**
 * Write a description of class Gale here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gale extends Weapon
{
    private static final int ult = 2000;
    public void fire(){
        Draft bullet = new Draft(getHolder().getTargetRotation(), getHolder());
        getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
        Draft bullet2 = new Draft(getHolder().getTargetRotation()+5, getHolder());
        getHolder().getWorld().addObject (bullet2, getHolder().getRealX(), getHolder().getRealY());
        Draft bullet3 = new Draft(getHolder().getTargetRotation()-5, getHolder());
        getHolder().getWorld().addObject (bullet3, getHolder().getRealX(), getHolder().getRealY());
        bullet2.setHitStory(bullet.getHitStory());
        bullet3.setHitStory(bullet.getHitStory());//projectiles share same reference to not damage twice
        //bullet.move ();
        Sounds.play("gunshoot");
    }
    public void fireUlt(){
        Tornado bullet = new Tornado(getHolder().getTargetRotation(), getHolder());
        getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
        Sounds.play("protonwave");
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        updateAmmo(1);
    }
    public Gale(GridObject actor){
        super(actor);
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(1, 0);
    }
    public String getName(){
        return "Gale";
    }
    public int getRarity(){
        return 2;
    }
}