package com.karel.game.weapons.jackolantern;

import com.karel.game.Greenfoot;
import com.karel.game.ItemHolder;
import com.karel.game.LandingHandler;
import com.karel.game.SimpleAmmoManager;
import com.karel.game.weapons.Weapon;
import com.karel.game.weapons.easterbasket.SurpriseEasterEgg;

public class JackOLantern extends Weapon implements LandingHandler
{
    private static final int gunReloadTime = 60;
    private static final int ult = 1000;
    private int smashCooldown = 15;
    private boolean toLand;
    public void fire(){
        if(continueUse()){
            if(!toLand){
                smashCooldown--;
                if(smashCooldown<=0){
                    double r = getHand().getTargetRotation(), d = 40;
                    getHolder().getWorld().addObject(new SmashedJack(getHolder()), getHolder().getBranchX(r, d)+getHolder().getX(), getHolder().getBranchY(r, d)+getHolder().getY());
                    setContinueUse(false);
                    setPlayerLockRotation(false);
                    setPlayerLockMovement(false);
                }
            }
        }
        else if (getAmmo().hasAmmo()) 
        {
            toLand = true;
            getHolder().initiateJump(getHand().getTargetRotation(), Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 220), 100);
            getAmmo().useAmmo();
            setContinueUse(true);
            setPlayerLockRotation(true);
            setPlayerLockMovement(true);
        }
    }
    public void fireUlt(){
        getHolder().addObjectHere(new PossessedPumpkinDropper(getHand().getTargetRotation(), Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 100), 50, getHolder(), new SurpriseEasterEgg(getHolder())));
    }
    public void spawnSurpriseEgg(){
        SurpriseEasterEgg egg = new SurpriseEasterEgg(getHolder());
        getHolder().getWorld().addObject(egg, getHolder().getX()+Greenfoot.getRandomNumber(500)-250, getHolder().getY()+Greenfoot.getRandomNumber(500)-250);
    }
    public void onGadgetActivate(){
        setGadgetTimer(100);
        for(int i = 0; i < 4; i++){
            spawnSurpriseEgg();
        }
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
        setAmmo(new SimpleAmmoManager(gunReloadTime, 1));
    }
    public String getName(){
        return "Jack-o-Lantern";
    }
    public int getRarity(){
        return 7;
    }
    public BotGuide getBotGuide(){
        return new BotGuide();
    }
    public class BotGuide extends Weapon.BotGuide{
        public int getEffectiveRange(){
            return 325;
        }
        public int getUltEffectiveRange(){
            return 350;
        }
        public int getUltIdealRange(){
            return 275;
        }
        //AVERAGE SPEED OF PROJECTILE
        public double getLead(double d){
            return 13;
        }
    }
}
