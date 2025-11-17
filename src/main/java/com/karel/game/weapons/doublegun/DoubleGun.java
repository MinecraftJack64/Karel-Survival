package com.karel.game.weapons.doublegun;

import com.karel.game.ItemHolder;
import com.karel.game.SimpleAmmoManager;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.hitters.Bullet;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Gun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DoubleGun extends Weapon
{
    private static final int gunReloadTime = 7;
    private boolean quickcharge = false;
    private static final int ult = 1900;
    private int secondUltDelay = 0; // 35
    public void fire(){
        if (getAmmo().hasAmmo()) 
        {
            Bullet bullet = new DoubleBullet (getHand().getTargetRotation(), getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            Bullet bullet2 = new DoubleBullet (getHand().getTargetRotation()-180, getHolder());
            getHolder().getWorld().addObject (bullet2, getHolder().getX(), getHolder().getY());
            if(useGadget()){
                Bullet bullet3 = new DoubleBullet (getHand().getTargetRotation()+90, getHolder());
                getHolder().getWorld().addObject (bullet3, getHolder().getX(), getHolder().getY());
                Bullet bullet4 = new DoubleBullet (getHand().getTargetRotation()-90, getHolder());
                getHolder().getWorld().addObject (bullet4, getHolder().getX(), getHolder().getY());
            }
            //bullet.move ();
            Sounds.play("gunshoot");
            getAmmo().useAmmo();
            getAmmo().donateAmmoBar(quickcharge?3:0);
            quickcharge = false;
        }
    }
    public void fireUlt(){
        if(continueUlt()){
            if(secondUltDelay>0){
                secondUltDelay--;
            }else{
                NeutronWave bullet = new NeutronWave(getHolder(), getUltUpgrade()==1?2:0);
                getHolder().getWorld().addObject(bullet, getHolder().getX(), getHolder().getY());
                secondUltDelay = 35;
                Sounds.play("neutronwave");
                setContinueUlt(false);
            }
        }else{
            NeutronWave bullet = new NeutronWave(getHolder(), getUltUpgrade()==1?1:0);
            getHolder().getWorld().addObject(bullet, getHolder().getX(), getHolder().getY());
            Sounds.play("neutronwave");
            setContinueUlt(true);
            secondUltDelay = 35;
        }
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        setGadgetTimer(360);
        trackGadget();
    }
    public DoubleGun(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(gunReloadTime, 1));
    }
    public int defaultGadgets(){
        return 3;
    }
    public String getName(){
        return "Double Gun";
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
        public boolean shouldUseUlt(){
            return true;
        }
    }
}




