package com.karel.game.weapons.snailshell;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.shields.ShieldID;
import com.karel.game.trackers.AmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class GrenadeLauncher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SnailShell extends Weapon
{
    private static final int gunReloadTime = 30;
    private int reloadDelayCount;
    private AmmoManager ammo, ammo2;
    private ShieldID shield = new ShieldID(this);
    private SnailShield shell;
    private SpeedPercentageEffect slow;
    private int ultd = 0;
    private int mantleCooldown = 0;
    private static final int ult = 2000;
    public void fire(){
        if(ultd>0){
            if(reloadDelayCount>=gunReloadTime&&ammo2.hasAmmo()){
                for(int i = -35; i <= 35; i+=10){
                    SaltShot bullet = new SaltShot(i+getHand().getTargetRotation(), getHolder());
                    getHolder().addObjectHere(bullet);
                }
                reloadDelayCount = 0;
                ammo2.useAmmo();
                openMantle();
            }
        }
        if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 400);
            SaltClump bullet = new SaltClump (getHand().getTargetRotation(), d, 300, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            Sounds.play("airtoss");
            reloadDelayCount = 0;
            ammo.useAmmo();
            openMantle();
        }
    }
    public void fireUlt(){
        if(ultd>0){
            cancelUltReset();
            return;
        }
        ultd = 150;
        shell = new SnailShield(shield, 0.75, ultd*2);
        getHolder().applyShield(shell);
        slow = new SpeedPercentageEffect(0.35, ultd, getHolder());
        getHolder().applyEffect(slow);
        newSpecial(ultd, ultd);
        setAmmo(ammo2);
    }
    public void endUlt(){
        if(slow!=null&&slow.isApplied()){
            slow.clear();
            slow = null;
        }
        if(getHolder().hasShield(shield))getHolder().removeShield(shield);
        disableSpecial();
        setAmmo(ammo);
    }
    public void openMantle(){
        mantleCooldown = 0;
        if(shell!=null)shell.setEffectiveness(0.75);
    }
    public void closeMantle(){
        if(shell!=null)shell.setEffectiveness(0.85);
    }
    public void reload(double speed){
        reloadDelayCount+=speed;
        if(mantleCooldown<15&&getUltUpgrade()==1&&ultd>0){
            mantleCooldown++;
            if(mantleCooldown==15){
                closeMantle();
            }
        }
        if(getHand().isMoving()){
            openMantle();
        }
        if(ultd==0||reloadDelayCount>=gunReloadTime)super.reload(speed);
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
        ammo = new AmmoManager(90, 2, 2);
        ammo2 = new AmmoManager(60, 3, 3);
        setAmmo(ammo);
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






