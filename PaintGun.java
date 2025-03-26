import greenfoot.*;
import java.util.List;

/**
 * Write a description of class PaintGun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PaintGun extends Weapon
{
    private static final int gunReloadTime = 30;
    private static final int ultReloadTime = 4;
    private int reloadDelayCount;
    private static final int ult = 2000;
    private int ultAmmo = 0; // 3
    private int startUltCooldown = 0; // 30
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            for(int i = -5; i <= 5; i+=2){
                PaintDrop bullet = new PaintDrop (getHand().getTargetRotation()+i, false, getHolder());
                getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            }
            //bullet.move ();
            reloadDelayCount = 0;
            Sounds.play("gunshoot");
        }
    }
    public void fireUlt(){
        if(continueUlt()){
            if(startUltCooldown>0){
                startUltCooldown--;
            }else{
                if(reloadDelayCount>=ultReloadTime){
                    reloadDelayCount = 0;
                    double spread = (ultAmmo+4)*1.5; // 8 at 3 ammo, 6 at 1 ammo
                    for(double i = -spread; i <= spread; i+=3){
                        PaintDrop bullet = new PaintDrop (getHand().getTargetRotation()+i, true, getHolder());
                        getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
                    }
                    ultAmmo--;
                    if(ultAmmo<=0){
                        setContinueUlt(false);
                        setPlayerLockRotation(false);
                    }
                }
            }
        }else{
            startUltCooldown = 30;
            reloadDelayCount = 0;
            ultAmmo = 3;
            setPlayerLockRotation(true);
            setContinueUlt(true);
        }
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public PaintGun(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Paint Sprayer";
    }
    public int getRarity(){
        return 1;
    }
    public BotGuide getBotGuide(){
        return new BotGuide();
    }
    private class BotGuide extends Weapon.BotGuide{
        public static int getEffectiveRange(){
            return 600;
        }
        public static int getUltEffectiveRange(){
            return 300;
        }
        public static int getUltIdealRange(){
            return 0;
        }
    }
}




