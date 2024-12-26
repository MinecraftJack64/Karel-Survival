import greenfoot.*;
/**
 * Write a description of class Highjacker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Highjacker extends Weapon
{
    private static final int ult = 2500;
    private Scissors lasso;
    private int ultchargecooldown = 0;
    public void fire(){//one full ammo deals 350 damage
        if (lasso == null) 
        {
            Scissors bullet = new Scissors(getHolder().getTargetRotation(), getHolder());
            getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
            //bullet.move ();
            lasso = bullet;
            Sounds.play("lifestealshoot");
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
        if(lasso!=null&&lasso.hasReturned()){
            lasso = null;
        }
    }
    public Highjacker(GridObject actor){
        super(actor);
    }
    public String getName(){
        return "Highjacker";
    }
    public int getRarity(){
        return 5;
    }
}
