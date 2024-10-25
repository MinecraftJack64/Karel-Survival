import greenfoot.*;
/**
 * Write a description of class Crossbow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Crossbow extends Weapon
{
    private static final int gunReloadTime = 15;
    private double focus = 0.5;//30 framse to reach 1.5
    private int reloadDelayCount;
    private static final int ult = 1500;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            double deg = 30-(focus-0.5)*25;
            boolean ug = getAttackUpgrade()==1;
            PoisonArrow mbullet = new PoisonArrow (getHolder().getTargetRotation(), focus, ug, holder);
            getHolder().getWorld().addObject (mbullet, getHolder().getRealX(), getHolder().getRealY());
            PoisonArrow rbullet = new PoisonArrow (getHolder().getTargetRotation()-deg, focus, ug, holder);
            getHolder().getWorld().addObject (rbullet, getHolder().getRealX(), getHolder().getRealY());
            PoisonArrow lbullet = new PoisonArrow (getHolder().getTargetRotation()+deg, focus, ug, holder);
            getHolder().getWorld().addObject (lbullet, getHolder().getRealX(), getHolder().getRealY());
            //bullet.move ();
            Sounds.play("crossbowshoot");
            reloadDelayCount = 0;
            focus = 0.5;
        }
    }
    public void fireUlt(){
        Sounds.play("crossbowshoot");
        for(int i = 1000; i <= 1350; i+=50){
            double x = getHolder().getTargetX()+(int)(Math.random()*41)-20;
            double y = getHolder().getTargetY()+(int)(Math.random()*41)-20;
            RainingPoisonArrow bullet = new RainingPoisonArrow(getHolder().getAngle(x, y)+90, getHolder().distanceTo(x, y), i, focus, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
        }
        focus = 0.5;
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        if(reloadDelayCount>gunReloadTime&&focus<1.5){
            focus+=0.025;
            updateAmmo((int)(focus*40));
        }
    }
    public Crossbow(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(60, (int)(focus*40));
    }
    public String getName(){
        return "Crossbow";
    }
    public int getRarity(){
        return 2;
    }
}
