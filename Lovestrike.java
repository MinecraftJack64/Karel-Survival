import greenfoot.*;
import java.util.List;

/**
 * Write a description of class Lovestrike here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lovestrike extends Weapon
{
    private static final int gunReloadTime = 40;
    private int reloadDelayCount;
    private static final int ult = 300;
    private int ultchargedelay = 0;
    private Heart heart;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            CupidArrow bullet = new CupidArrow (getHolder().getTargetRotation(), getHolder(), this);
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            //bullet.move ();
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    public void fireUlt(){
        Kiss bullet = new Kiss(getHolder().getTargetRotation(), getHolder(), this);
        getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
        Sounds.play("protonwave");
    }
    public void notifyHit(Heart n){
        if(heart!=null&&!heart.isDead()){
            heart.die();
        }
        heart = n;
    }
    public boolean hasHeartActive(){
        return heart!=null&&!heart.isDead();
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
        if(ultchargedelay<=0){
            List<GridEntity> l = getHolder().getGEsInRange(600);
            for(GridEntity g:l){
                if(g!=null&&getHolder().isAggroTowards(g)&&g.willNotify(getHolder())){
                    chargeUlt(7);
                }
            }
            ultchargedelay = 5;
        }else{
            ultchargedelay--;
        }
    }
    public Lovestrike(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Lovestrike";
    }
    public int getRarity(){
        return 7;
    }
}