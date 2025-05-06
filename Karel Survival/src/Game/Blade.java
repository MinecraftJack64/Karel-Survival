package Game;
import greenfoot.*;
/**
 * Write a description of class Blade here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Blade extends Weapon
{
    private static final int gunReloadTime = 20;
    private int reloadDelayCount;
    private AmmoManager ammo;
    private static final int ult = 3000;
    private int remainingslices = 160;
    private int slicecooldown = 0;//2 by default
    private boolean nextdir = false;
    private int nextstabdir;//0, 45, 90...
    private ShieldID shield = new ShieldID(this);
    public void fire(){
        if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            ammo.useAmmo();
            Sword bullet = new Sword(getHand().getTargetRotation(), ammo.getAmmo(), nextdir, getAttackUpgrade()==1, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            //bullet.move ();
            nextdir = !nextdir;
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    public void fireUlt(){
        if(!continueUlt()){
            getHolder().applyEffect(new SpeedPercentageEffect(1.5, 160, getHolder(), new EffectID(getHolder(), "ultspeed")));
            getHolder().applyShield(new PercentageShield(shield, 0.5, 160));
            setContinueUlt(true);
            slicecooldown = 2;
            remainingslices = 160;
            fireSword();
        }else{
            fireSword();
            if(remainingslices<=0){
                setContinueUlt(false);
            }
        }
    }
    public void fireSword(){
        remainingslices--;
        CuttingSword s = new CuttingSword(nextstabdir, getHolder());
        nextstabdir+=45;
        nextstabdir%=360;
        getHolder().addObjectHere(s);
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            ammo.reload();
        }
        updateAmmo(ammo.getAmmoBar());
    }
    public Blade(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new AmmoManager(25, 3, 3);
        chargeUlt(10000);
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(ammo.getMaxAmmoBar(), ammo.getAmmoBar(), ammo.getMaxAmmo());
    }
    public String getName(){
        return "Blade";
    }
    public int getRarity(){
        return 0;
    }
}




