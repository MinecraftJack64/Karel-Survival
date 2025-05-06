package Game;
import greenfoot.*;
import java.util.List;

/**
 * Write a description of class Pointpinner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pointpinner extends Weapon
{
    private static final int gunReloadTime = 30;
    private int ultcooldown = 0;//5
    private int reloadDelayCount;
    private static final int ult = 2000;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            Pin bullet = new Pin(getHand().getTargetRotation(), getHolder(), this);
            getHolder().addObjectHere(bullet);
            reloadDelayCount = 0;
            //bullet.move ();
            Sounds.play("gunshoot");
        }
    }
    public void fireUlt(){
        if(ultcooldown<=0){
            Pinpoint bullet = new Pinpoint(getUltUpgrade()==1, getHolder());
            getHolder().getWorld().addObject(bullet, getHand().getTargetX(), getHand().getTargetY());
            ultcooldown = 5;
        }else{
            cancelUltReset();
        }
    }
    public void notifyHit(){
        if(getAttackUpgrade()==1)reloadDelayCount+=15;
    }
    public int getUltMaxUses(){
        return getHolder().getGEsInRange(250).size()+1;
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        if(ultcooldown>0){
            ultcooldown--;
        }
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public Pointpinner(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Pointpinner";
    }
    public int getRarity(){
        return 2;
    }
}






