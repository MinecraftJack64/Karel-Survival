package com.karel.game.weapons.flail;
import com.karel.game.Dasher;
import com.karel.game.DasherDoer;
import com.karel.game.ItemHolder;
import com.karel.game.weapons.Weapon;
import com.karel.game.SimpleAmmoManager;

/**
 * Write a description of class Flail here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Flail extends Weapon
{
    private static final int gunReloadTime = 60;
    private Dasher dash;
    private FlailCore core;
    private static final int ult = 900;
    public void fire(){//one full ammo deals 350 damage
        if(continueUse()){
            if(core.hasReturned()){
                setContinueUse(false);
                setPlayerLockMovement(false);
                setPlayerLockRotation(false);
            }
        }else{
            if(getAmmo().hasAmmo()){
                double d = Math.max(50, Math.min(150, getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY())));
                core = new FlailCore(getHand().getTargetRotation(), d, d/3, getHolder(), getAttackUpgrade()==1);
                getHolder().addObjectHere(core);
                setContinueUse(true);
                setPlayerLockMovement(true);
                setPlayerLockRotation(true);
                chargeUlt(60);
                getAmmo().useAmmo();
            }
        }
    }
    public void fireUlt(){
        if(continueUlt()){
            if(!dash.dash()){
                dash = null;
                setContinueUlt(false);
                setPlayerLockRotation(false);
            }else if(getAttackUpgrade()==1){
                getHolder().explodeOn(150, (g)->{
                    if(getHolder().isAggroTowards(g)&&g.canBePulled())g.pullTowards(getHolder(), 12);
                }, null);
            }
        }else if(continueUse()){
            cancelUltReset();
        }
        else 
        {
            dash = new DasherDoer(getHand().getTargetRotation(), 16, 31, 100, (g)->{
                if(getHolder().isAggroTowards(g)){
                    if(g.canBePulled())g.knockBack(getHolder().face(g, false)+90, getHolder().distanceTo(g)*1.5, 25, getHolder());
                    getHolder().damage(g, 300, 0.6);
                }
            }, getHolder());
            //Old: dash = new DasherDoer(getHand().getTargetRotation(), 20, 10, 60, 150, getHolder());
            setContinueUlt(true);
            setPlayerLockRotation(true);
            dash.dash();
            getHolder().heal(getHolder(), 100);
        }
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        getHolder().explodeOnEnemies(150, (g)->{
            if(g.hasShield()&&g.getShield().canBreak()){
                g.getShield().damage(g.getShield().getHealth(), getHolder());
                if(g.canBePulled()){
                    g.knockBack(getHolder().face(g, false)+90, getHolder().distanceTo(g)*1.5, 25, getHolder());
                }
            }
        });
        setGadgetTimer(100);
    }
    public boolean canActivateGadget(){
        return super.canActivateGadget()&&(core==null||core.hasReturned())&&dash==null;
    }
    public int defaultGadgets(){
        return 2;
    }
    public Flail(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(gunReloadTime, 1));
    }
    public void equip(){
        super.equip();
    }
    public String getName(){
        return "Flail";
    }
    public int getRarity(){
        return 3;
    }
}






