package com.karel.game.weapons.gun;

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
public class Gun extends Weapon
{
    private static final int gunReloadTime = 5;
    private boolean quickcharge = false;
    private static final int ult = 1750;
    public void fire(){
        if (getAmmo().hasAmmo()) 
        {
            Bullet bullet = new Bullet (getHand().getTargetRotation(), getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            //bullet.move ();
            Sounds.play("gunshoot");
            getAmmo().useAmmo();
            getAmmo().donateAmmoBar((quickcharge?3:0)+(useGadget()?2:0));
            if(getAttackUpgrade()==1){
                quickcharge = !quickcharge;
            }
        }
    }
    public void fireUlt(){
        ProtonWave bullet = new ProtonWave(getHolder(), getUltUpgrade()==1);
        getHolder().getWorld().addObject(bullet, getHolder().getX(), getHolder().getY());
        Sounds.play("protonwave");
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        setGadgetTimer(360);
    }
    public Gun(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(gunReloadTime, 1));
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




