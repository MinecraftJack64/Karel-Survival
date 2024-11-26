import greenfoot.*;
import java.util.List;

/**
 * Write a description of class GlueGun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GlueGun extends Weapon
{
    private int reloadDelayCount;
    private static final int ult = 2000;
    private int dropsremaining = 0;
    private int explodecooldown = -1;
    public void fire(){
        if(continueUse()){
            double d = Math.min(getHolder().distanceTo(getHolder().getTargetX(), getHolder().getTargetY()), getRange());
            GlueDrop bullet = new GlueDrop(getHolder().getTargetRotation(), d, getFocus(), getHolder());
            getHolder().addObjectHere(bullet);
            dropsremaining--;
            if(dropsremaining<=0){
                setContinueUse(false);
                setPlayerLockRotation(false);
            }
        }
        else if (reloadDelayCount >= getReloadTime()) 
        {
            double d = Math.min(getHolder().distanceTo(getHolder().getTargetX(), getHolder().getTargetY()), getRange());
            GlueDrop bullet = new GlueDrop(getHolder().getTargetRotation(), d, getFocus(), getHolder());
            getHolder().addObjectHere(bullet);
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
            if(getNumDrops()>1){
                setContinueUse(true);
                setPlayerLockRotation(true);
                dropsremaining = getNumDrops()-1;
            }
        }
    }
    public int getReloadTime(){
        return (int)(30-getFocus()*20);
    }
    public double getRange(){
        return 275+getFocus()*100;
    }
    public int getNumDrops(){
        return (int)(getFocus()*4+1);
    }
    public double getFocus(){
        return getUltCharge()*1.0/getUlt();
    }
    public void fireUlt(){
        if(!continueUlt()){
            explodecooldown = 20;
            setContinueUlt(true);
        }else{
            explodecooldown--;
            if(explodecooldown<=0){
                explodecooldown = -1;
                getHolder().explodeOnEnemies(225, (g)->{
                    g.applyeffect(new SpeedPercentageEffect(0.1, 170));
                    g.applyeffect(new SilenceEffect(170, getHolder()));
                    double d = getHolder().distanceTo(g)+100;
                    GlueDrop bullet = new GlueDrop(getHolder().face(g, false), d, 5, getHolder());
                    getHolder().addObjectHere(bullet);
                    g.knockBack(getHolder().face(g, false), 100, 30, getHolder());
                });
                setContinueUlt(false);
            }
        }
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        setGadgetTimer(120);
    }
    public void reload(){
        reloadDelayCount++;
        //updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
        //chargeUlt(10000);
    }
    public GlueGun(GridObject actor){
        super(actor);
        //reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        //getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public int defaultGadgets(){
        return 3;
    }
    public String getName(){
        return "Glue Gun";
    }
    public int getRarity(){
        return 2;
    }
}
