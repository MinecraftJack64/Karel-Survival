import greenfoot.*;
/**
 * Write a description of class Flail here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Flail extends Weapon implements AmmoHolder
{
    private static final int gunReloadTime = 60;
    private Dasher dash;
    private FlailCore core;
    private int reloadDelayCount;
    private static final int ult = 900;
    public void fire(){//one full ammo deals 350 damage
        if(continueUse()){
            if(core.hasReturned()){
                setContinueUse(false);
                setPlayerLockMovement(false);
                setPlayerLockRotation(false);
            }
        }else{
            if(reloadDelayCount>=gunReloadTime){
                double d = Math.max(50, Math.min(150, getHolder().distanceTo(getHolder().getTargetX(), getHolder().getTargetY())));
                core = new FlailCore(getHolder().getTargetRotation(), d, d/3, getHolder());
                getHolder().addObjectHere(core);
                setContinueUse(true);
                setPlayerLockMovement(true);
                setPlayerLockRotation(true);
                chargeUlt(60);
                reloadDelayCount = 0;
            }
        }
    }
    public void fireUlt(){
        if(continueUlt()){
            if(!dash.dash()){
                dash = null;
                setContinueUlt(false);
                setPlayerLockRotation(false);
            }
        }else if(continueUse()){
            cancelUltReset();
        }
        else 
        {
            if(getAttackUpgrade()==1){
                dash = new DasherDoer(getHolder().getTargetRotation(), 20, 25, 120, (g)->{
                    if(getHolder().isAggroTowards(g)){
                        if(g.canBePulled())g.knockBack(getHolder().face(g, false)+90, getHolder().distanceTo(g)*1.5, 25, getHolder());
                        getHolder().damage(g, 300);
                    }
                }, getHolder());
            }else{
                dash = new DasherDoer(getHolder().getTargetRotation(), 20, 10, 60, 150, getHolder());
            }
            setContinueUlt(true);
            setPlayerLockRotation(true);
            dash.dash();
            getHolder().heal(getHolder(), 100);
            //bullet.move ();
            Sounds.play("reap");
        }
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(gunReloadTime, reloadDelayCount));
    }
    public Flail(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Flail";
    }
    public int getRarity(){
        return 3;
    }
}