package com.karel.game.weapons.snailshell;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.shields.ShieldID;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class GrenadeLauncher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SnailShell extends Weapon
{
    private static final int gunReloadTime = 90;
    private int reloadDelayCount;
    private ShieldID shield = new ShieldID(this);
    private SpeedPercentageEffect slow;
    private int ultd = 0;
    private static final int ult = 2000;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 350);
            SaltClump bullet = new SaltClump (getHand().getTargetRotation(), d, 300, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            Sounds.play("airtoss");
            reloadDelayCount = 0;
        }
    }
    public void fireUlt(){
        ultd = 150;
        getHolder().applyShield(new SnailShield(shield, 0.75, ultd+1));
        slow = new SpeedPercentageEffect(0.35, ultd, getHolder());
        getHolder().applyEffect(slow);
        newSpecial(ultd, ultd);
        /*if(getUltUpgrade()==1){
            for(int i = 0; i < 360; i+=90){
                getHolder().addObjectHere(new Sandbag(getHolder().getTargetRotation()+i, getHolder()));
            }
        }*/
    }
    public void endUlt(){
        if(slow!=null&&slow.isApplied()){
            slow.clear();
            slow = null;
        }
        if(getHolder().hasShield(shield))getHolder().removeShield(shield);
        disableSpecial();
    }
    public void reload(double speed){
        reloadDelayCount+=speed;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public void update(){
        super.update();
        if(ultd>0){
            ultd--;
            if(ultd<=0){
                endUlt();
            }
        }
    }
    public void onGadgetActivate(){
        setGadgetCount(1);
    }
    public int defaultGadgets(){
        return 2;
    }
    public int getUlt(){
        return ult;
    }
    public SnailShell(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(gunReloadTime, reloadDelayCount);
    }
    public void unequip(){
        endUlt();
        super.unequip();
    }
    public String getName(){
        return "Snail Shell";
    }
    public int getRarity(){
        return 1;
    }
}






