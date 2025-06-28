package com.karel.game.weapons.blade;

import com.karel.game.AmmoManager;
import com.karel.game.Game;
import com.karel.game.ItemHolder;
import com.karel.game.PercentageShield;
import com.karel.game.Sounds;
import com.karel.game.SpeedPercentageEffect;
import com.karel.game.weapons.EffectID;
import com.karel.game.weapons.ShieldID;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Blade here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Blade extends Weapon
{
    private static final int gunReloadTime = 20;
    private int reloadDelayCount;
    private AmmoManager ammo;
    private static final int ult = 3000;
    private int remainingslices = 160;
    private boolean nextdir = false;
    private int nextstabdir;//0, 45, 90...
    private int bonusSlices = 80;
    private ShieldID shield = new ShieldID(this);
    public void fire(){
        if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            ammo.useAmmo();
            Sword bullet = new Sword(getHand().getTargetRotation(), ammo.getAmmo(), nextdir, getAttackUpgrade()==1, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            //bullet.move ();
            nextdir = !nextdir;
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    public void fireUlt(){
        if(!continueUlt()){
            getHolder().applyEffect(new SpeedPercentageEffect(1.5, 160, getHolder(), new EffectID(getHolder(), "ultspeed")));
            getHolder().applyShield(new PercentageShield(shield, 0.5, 160));
            setContinueUlt(true);
            remainingslices = 160;
            fireSword();
        }else{
            fireSword();
            if(remainingslices<=0){
                setContinueUlt(false);
                if(continueGadget()){
                    setContinueGadget(false);
                    bonusSlices = 80;
                }
            }
        }
    }
    public void fireSword(){
        remainingslices--;
        CuttingSword s = new CuttingSword(nextstabdir, getHolder());
        nextstabdir+=45;
        nextstabdir%=360;
        getHolder().addObjectHere(s);
        if(continueGadget()&&getHolder().distanceTo(getHolder().getNearestTarget())<120){
            remainingslices+=bonusSlices;
            bonusSlices/=2;
        }
        getHolder().explodeOn(120, (e) -> {
            if(getUltUpgrade()==1)if(getHolder().distanceTo(e)>20)e.pullTowards(getHolder(), 2);
            getHolder().damage(e, 20-(int)getHolder().distanceTo(e)/6);
            if(e.isDead()&&e.willNotify(s)){
                s.notifyDamage(e, 2000);
            }
        });
    }
    public int getUlt(){
        return ult;
    }
    public void reload(double speed){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            super.reload(speed);
        }
    }
    public Blade(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new AmmoManager(25, 3, 3);
        setAmmo(ammo);
        chargeUltFull();
    }
    public void equip(){
        super.equip();
        Game.gameUI().newAmmo(ammo.getMaxAmmoBar(), ammo.getAmmoBar(), ammo.getMaxAmmo());
    }
    public String getName(){
        return "Blade";
    }
    public int getRarity(){
        return 0;
    }
}




