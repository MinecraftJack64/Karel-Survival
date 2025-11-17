package com.karel.game.weapons.jackolantern;

import com.karel.game.AmmoManager;
import com.karel.game.ItemHolder;
import com.karel.game.LandingHandler;
import com.karel.game.weapons.Weapon;

public class JackOLantern extends Weapon implements LandingHandler
{
    private static final int gunReloadTime = 60;
    private static final int ult = 900;
    private int smashCooldown = 15;
    private boolean toLand;
    public void fire(){
        if(continueUse()){
            if(!toLand){
                smashCooldown--;
                if(smashCooldown<=0){
                    double r = getHand().getTargetRotation()-90, d = 40;
                    getHolder().getWorld().addObject(new SmashedJack(getHolder(), getAttackUpgrade()==1), getHolder().getBranchX(r, d)+getHolder().getX(), getHolder().getBranchY(r, d)+getHolder().getY());
                    setContinueUse(false);
                    setPlayerLockMovement(false);
                }
            }
        }
        else if (getAmmo().hasAmmo()&&!isUsingGadget()) 
        {
            toLand = true;
            getHolder().initiateJump(getHand().getTargetRotation(), Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 220), 100);
            getAmmo().useAmmo();
            setContinueUse(true);
            setPlayerLockMovement(true);
        }
    }
    public void fireUlt(){
        getHolder().addObjectHere(new PossessedPumpkinDropper(getHand().getTargetRotation(), Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 200), 50, new PossessedPumpkin(getHolder(), getUltUpgrade()==1), getHolder()));
    }
    public void onGadgetActivate(){
        setGadgetTimer(180);
        trackGadget();
        getHolder().addObjectHere(new SmashedJack(getHolder(), 6));
        SmashedJack s = new SmashedJack(getHolder(), 6);
        getHolder().addObjectHere(s);
        getHolder().mount(s);
    }
    public void update(){
        chargeUlt(1);
        super.update();
    }
    public int defaultGadgets(){
        return 1;
    }
    public void doLanding(){
        if(toLand){
            toLand = false;
            smashCooldown = 15;
        }
    }
    public int getUlt(){
        return ult;
    }
    public JackOLantern(ItemHolder actor){
        super(actor);
        setAmmo(new AmmoManager(gunReloadTime, 1, 2));
    }
    public String getName(){
        return "Jack-o-Lantern";
    }
    public int getRarity(){
        return 7;
    }
}
