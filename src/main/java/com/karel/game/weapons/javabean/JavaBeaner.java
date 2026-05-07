package com.karel.game.weapons.javabean;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.trackers.AmmoManager;
import com.karel.game.trackers.SimpleAmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Scream here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JavaBeaner extends Weapon
{
    private static final int gunReloadTime = 15;
    private static final int ultReloadTime = 8;
    private int reloadDelayCount;
    private static final int ult = 2000;
    private int ultAmmo = 0; // 10
    private int startUltCooldown = 0; // 30
    private AmmoManager[] ammos = new AmmoManager[5];
    public void fire(){
        if (reloadDelayCount>=gunReloadTime&&hasAmmo()) 
        {
            JavaBean bullet = getProjectile(getHand().getTargetRotation());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            useAmmo();
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    public void fireUlt(){
        if(continueUlt()){
            if(startUltCooldown>0){
                startUltCooldown--;
            }else{
                getHolder().pull(getHand().getTargetRotation()-180, 2);
                if(reloadDelayCount>=ultReloadTime){
                    reloadDelayCount = 0;
                    CoffeeTorrent bullet = new CoffeeTorrent(getHand().getTargetRotation(), getUltUpgrade()==1, getHolder());
                    getHolder().addObjectHere(bullet);
                    ultAmmo--;
                    if(ultAmmo<=0){
                        onInterrupt();
                    }
                }
            }
        }else{
            startUltCooldown = 15;
            reloadDelayCount = 0;
            ultAmmo = 10;
            setContinueUlt(true);
        }
    }
    public void onInterrupt(){
        setContinueUlt(false);
    }
    public void useAmmo(){
        for(AmmoManager m: ammos){
            if(m.hasAmmo()){
                m.useAmmo();
                break;
            }
        }
    }
    public boolean hasAmmo(){
        for(AmmoManager m: ammos){
            if(m.hasAmmo()){
                return true;
            }
        }
        return false;
    }
    public void onGadgetActivate(){
        int ct = 0;
        while(hasAmmo()){
            useAmmo();
            ct++;
        }
        getHolder().applyEffect(new SpeedPercentageEffect(1.25+0.05*ct, ct*30, getHolder()));
        getHolder().heal(getHolder(), ct*100);
        setGadgetTimer(30);
    }
    public boolean canActivateGadget(){
        return super.canActivateGadget()&&hasAmmo();
    }
    public int defaultGadgets(){
        return 2;
    }
    public JavaBean getProjectile(double rotation){
        return getAttackUpgrade()==1?new PoisonousJavaBean(rotation, getHolder()):new JavaBean(rotation, getHolder());
    }
    public int getUlt(){
        return ult;
    }
    public void reload(double speed){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            for(AmmoManager m: ammos){
                m.reload(speed);
            }
        }
    }
    public JavaBeaner(ItemHolder actor){
        super(actor);
        for(int i = 0; i < ammos.length; i++){
            ammos[i] = new SimpleAmmoManager(150, 1);
        }
    }
    public String getName(){
        return "Java Beaner";
    }
    public int getRarity(){
        return 2;
    }
    /*public BotGuide getBotGuide(){
        return new BotGuide();
    }
    private class BotGuide extends Weapon.BotGuide{
        public static int getEffectiveRange(){
            return 600;
        }
        public static int getUltEffectiveRange(){
            return 300;
        }
        public static int getUltIdealRange(){
            return 0;
        }
    }*/
}




