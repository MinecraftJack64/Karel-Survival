package com.karel.game.weapons.crossbow;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;
import com.karel.game.Greenfoot;

/**
 * Write a description of class Crossbow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Crossbow extends Weapon
{
    private static final int gunReloadTime = 15;
    private int ultDelay = 0;
    private double focus = 0.5;//30 framse to reach 1.5
    private double reloadDelayCount;
    private static final int ult = 1500;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            double deg = 30-(focus-0.5)*25;
            boolean ug = getAttackUpgrade()==1;
            PoisonArrow mbullet = new PoisonArrow (getHand().getTargetRotation(), focus, ug, getHolder());
            getHolder().getWorld().addObject (mbullet, getHolder().getX(), getHolder().getY());
            PoisonArrow rbullet = new PoisonArrow (getHand().getTargetRotation()-deg, focus, ug, getHolder());
            getHolder().getWorld().addObject (rbullet, getHolder().getX(), getHolder().getY());
            PoisonArrow lbullet = new PoisonArrow (getHand().getTargetRotation()+deg, focus, ug, getHolder());
            getHolder().getWorld().addObject (lbullet, getHolder().getX(), getHolder().getY());
            //bullet.move ();
            Sounds.play("crossbowshoot");
            reloadDelayCount = 0;
            if(!useGadget())focus = 0.5;
        }
    }
    public void fireUlt(){
        if(continueUlt()){
            if(ultDelay>0){
                ultDelay--;
                return;
            }
            fireRainingArrows(true);
            onInterrupt();
        }
        else if(getUltUpgrade()==1){
            setContinueUlt(true);
            setPlayerLockRotation(true);
            getHand().setTargetLock(true);
            fireRainingArrows(true);
            ultDelay = 35;
        }else{
            fireRainingArrows(false);
        }
        Sounds.play("crossbowshoot");
    }
    public void onInterrupt(){
        setContinueUlt(false);
        setPlayerLockRotation(false);
        getHand().setTargetLock(false);
    }
    //constant - if height is same, they will land at same time.
    public void fireRainingArrows(boolean constant){
        for(int i = 1000; i <= 1700; i+=100){
            double x = getHand().getTargetX()+(int)(Greenfoot.getRandomNumber()*80*(2-focus))-20;
            double y = getHand().getTargetY()+(int)(Greenfoot.getRandomNumber()*80*(2-focus))-20;
            RainingPoisonArrow bullet = new RainingPoisonArrow(getHolder().getAngle(x, y)+90, Math.min(getHolder().distanceTo(x, y), 1000), constant?1000:i, focus, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
        }
        if(!useGadget())focus = 0.5;
    }
    public void onGadgetActivate(){
        focus = 1.5;
        setGadgetCount(4);
    }
    public int defaultGadgets(){
        return 5;
    }
    public int getUlt(){
        return ult;
    }
    public void reload(double speed){
        reloadDelayCount+=speed;
        if(reloadDelayCount>gunReloadTime&&focus<1.5&&!continueUlt()){
            focus+=0.025;
            updateAmmo((int)(focus*40));
        }
    }
    public Crossbow(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(60, (int)(focus*40));
    }
    public String getName(){
        return "Crossbow";
    }
    public int getRarity(){
        return 2;
    }
    public BotGuide getBotGuide(){
        return new BotGuide();
    }
    public class BotGuide extends Weapon.BotGuide{
        public int getEffectiveRange(){
            return 400;
        }
        public boolean shouldUse(){
            return getHolder().distanceTo(getHolder().getTarget())<=20*(5+focus*10);
        }
        public int getNumTargets(){
            return (int)((focus-0.5)*4)+1;
        }
        public int getUltEffectiveRange(){
            return 1000;
        }
        public int getUltNumTargets(){
            return -1;
        }
        public boolean shouldUseUlt(){
            return true;
        }
    }
}







