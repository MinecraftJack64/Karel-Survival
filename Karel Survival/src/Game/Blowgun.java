package Game;
import greenfoot.*;
import java.util.HashMap;

/**
 * Write a description of class Blowgun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Blowgun extends Weapon
{
    private static final int gunReloadTime = 10;
    private static final int ammoReloadTime = 30;
    private static final int maxAmmo = 20;
    private static final int ammoLimit = 5;
    private int reloadDelayCount;
    private int ammo;
    private int ammoReloadDelay;
    private static final int ult = 1250;
    private HashMap<GridEntity, Integer> scores = new HashMap<GridEntity, Integer>();
    public void fire(){//one full ammo deals 350 damage
        if (reloadDelayCount >= gunReloadTime&&ammo>0) 
        {
            Blowdart bullet = getAttackUpgrade()==1?new UpgradedBlowdart(getHand().getTargetRotation(), scores, getHolder()): new Blowdart(getHand().getTargetRotation(), scores, getHolder());
            getHolder().addObjectHere(bullet);
            //bullet.move ();
            Sounds.play("fireworkshoot");
            reloadDelayCount = 0;
            ammo--;
        }
    }
    public void fireUlt(){
        PocketKnife bullet = new PocketKnife(getHand().getTargetRotation(), 120, 60, scores, this, getHolder());
        getHolder().addObjectHere(bullet);
        Sounds.play("lassoshoot");
    }
    public void notifySlash(GridEntity targ){
        ammo+=3;
        if(ammo>maxAmmo){
            ammo = maxAmmo;
        }
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            if(ammo<ammoLimit){
                ammoReloadDelay++;
                if(ammoReloadDelay>=ammoReloadTime){
                    ammo++;
                    ammoReloadDelay = 0;
                }
            }
        }
        updateAmmo(ammo*ammoReloadTime+ammoReloadDelay);
    }
    public Blowgun(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammoReloadDelay = 0;
        ammo = 5;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(maxAmmo*ammoReloadTime, ammo*ammoReloadTime+ammoReloadDelay, maxAmmo);
    }
    public String getName(){
        return "Blowgun";
    }
    public int getRarity(){
        return 2;
    }
}




