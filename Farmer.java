import greenfoot.*;
/**
 * Write a description of class Farmer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Farmer extends Weapon
{
    private static final int gunReloadTime = 5;
    private int reloadDelayCount;
    private SimpleAmmoManager ammo;
    private InvisibilityEffect invis;
    private Scarecrow sc;
    private static final int ult = 2500;
    private int remainingslices = 160;
    private boolean nextdir = false;
    private ShieldID shield = new ShieldID(this);
    public void fire(){
        if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            ammo.useAmmo();
            Hoe bullet = new Hoe(getHand().getTargetRotation(), getAttackUpgrade()==1, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    public void fireUlt(){
        if(!continueUlt()){
            invis = new InvisibilityEffect(-1, getHolder(), new EffectID(this));
            getHolder().applyEffect(invis);
            sc = new Scarecrow(getHolder());
            getHolder().addObjectHere(sc);
            setContinueUlt(true);
        }else{
            if(sc.isDead()){
                sc = null;
                invis.clear();
                invis = null;
                setContinueUlt(false);
            }
        }
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
    public Farmer(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new SimpleAmmoManager(25, 1);
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(ammo.getMaxAmmoBar(), ammo.getAmmoBar(), ammo.getMaxAmmo());
    }
    public String getName(){
        return "Farmer";
    }
    public int getRarity(){
        return 5;
    }
    public boolean allowAttackWhileContinueUlt(){
        return true;
    }
}




