package Game;
import greenfoot.*;
import java.util.List;

/**
 * Write a description of class Gun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gun extends Weapon
{
    private static final int gunReloadTime = 5;
    private boolean quickcharge = false;
    private int reloadDelayCount;
    private static final int ult = 1750;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            Bullet bullet = new Bullet (getHand().getTargetRotation(), getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            //bullet.move ();
            Sounds.play("gunshoot");
            reloadDelayCount = (quickcharge?3:0)+(useGadget()?2:0);
            if(getAttackUpgrade()==1){
                quickcharge = !quickcharge;
            }
        }
    }
    public void fireUlt(){
        ProtonWave bullet = new ProtonWave(getHolder(), getUltUpgrade()==1);
        getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
        Sounds.play("protonwave");
        /*List<GridEntity> l = getHolder().getGEsInRange(185);
        for(GridEntity g:l){
            if(getHolder().isAggroTowards(g)){
                getHolder().damage(g, 700);
                //g.applyeffect(new PoisonEffect(30, getHolder()));
            }
        }*/
        //Explosion exp = new Explosion(3);
        //getHolder().addObjectHere(exp);
        //Sounds.play("explode");
        reloadDelayCount = 0;
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        setGadgetTimer(120);
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public Gun(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public int defaultGadgets(){
        return 3;
    }
    public String getName(){
        return "Minigun";
    }
    public int getRarity(){
        return 0;
    }
}




