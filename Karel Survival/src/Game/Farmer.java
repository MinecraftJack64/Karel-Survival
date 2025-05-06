package Game;
import greenfoot.*;
import java.util.ArrayList;

/**
 * Write a description of class Farmer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Farmer extends Weapon
{
    private static final int gunReloadTime = 10;
    private int reloadDelayCount;
    private int ultBeeperDelay = 0;
    private SimpleAmmoManager ammo;
    private InvisibilityEffect invis;
    private Scarecrow sc;
    private static final int ult = 2500;
    private int remainingslices = 160;
    private boolean nextdir = false;
    private ShieldID shield = new ShieldID(this);
    private ArrayList<Collectible> account = new ArrayList<Collectible>();
    public void fire(){
        if(continueUse()){
            for(int i = account.size()-1; i >= 0; i--){
                if(account.get(i).collected()){
                    account.remove(i);
                    chargeUlt(50);
                }
            }
            if(account.size()==0)setContinueUse(false);
            return;
        }
        if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            ammo.useAmmo();
            Hoe bullet = new Hoe(getHand().getTargetRotation(), getAttackUpgrade()==1, this, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            getHolder().addObjectHere(new HarvestBeeper(this));
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
            boolean gad = useGadget();
            if(getAttackUpgrade()==1){
                for(Collectible go: getHolder().getGOsInRange(gad?1000:110, Collectible.class)){
                    go.setRange(gad?1000:200);
                    if(gad&&go.getTarget()==getHolder())account.add(go);
                }
            }
            if(gad)setContinueUse(true);
            if(continueUlt()){
                sc.kill(getHolder());
            }
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
            if(getUltUpgrade()==1){
                if(ultBeeperDelay>=gunReloadTime){
                    getHolder().addObjectHere(new UltHarvestBeeper(this));
                    ultBeeperDelay = 0;
                }else ultBeeperDelay++;
            }
            if(sc.isDead()){
                sc = null;
                invis.clear();
                invis = null;
                setContinueUlt(false);
            }
        }
    }
    public void notifyBeeperCollect(){
        chargeUlt(50);
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
    public void onGadgetActivate(){
        setGadgetCount(1);
    }
    public int defaultGadgets(){
        return 2;
    }
    public Farmer(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new SimpleAmmoManager(35, 1);
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




