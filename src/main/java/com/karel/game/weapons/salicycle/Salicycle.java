package com.karel.game.weapons.salicycle;

import com.karel.game.AmmoManager;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Gun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Salicycle extends Weapon
{
    private static final int gunReloadTime = 120;
    private static final int ult = 2200;
    private int unloadDelay = 40;
    public void fire(){
        if (getAmmo().hasAmmo()&&unloadDelay<=0)
        {
            SalicylicBaton bullet = new SalicylicBaton(getHand().getTargetRotation(), (int)getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            Sounds.play("gunshoot");
            getAmmo().useAmmo();
            unloadDelay = 15;
        }
    }
    public void reload(double s){
        unloadDelay--;
        super.reload(s);
    }
    public void fireUlt(){
        getAmmo().donateAmmo(1);
        getHolder().addObjectHere(new SaliSwab(getHand().getTargetRotation(), getHolder()));
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        setGadgetTimer(360);
    }
    public Salicycle(ItemHolder actor){
        super(actor);
        setAmmo(new AmmoManager(gunReloadTime, 3, 3));
    }
    public int defaultGadgets(){
        return 3;
    }
    public String getName(){
        return "Salicycle";
    }
    public int getRarity(){
        return 0;
    }
    public BotGuide getBotGuide(){
        return new BotGuide();
    }
    public class BotGuide extends Weapon.BotGuide{
        public int getEffectiveRange(){
            return 600;
        }
        public int getUltEffectiveRange(){
            return 125;
        }
        public int getUltIdealRange(){
            return 0;
        }
        //AVERAGE SPEED OF PROJECTILE
        public double getLead(double d){
            return 15;
        }
        //TODO: requires number of nearby enemies, more when farther, just one when close
        public boolean shouldUseUlt(){
            return true;
        }
    }
}




