package com.karel.game.weapons.airpump;
import com.karel.game.ItemHolder;
import com.karel.game.LandingHandler;
import com.karel.game.SimpleAmmoManager;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class AirPump here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AirPump extends Weapon implements LandingHandler
{
    private static final int gunReloadTime = 40;
    private int secondWind = 0;
    private static final int ult = 225;
    private boolean nextIsSuper = false;
    public void fire(){
        if (getAmmo().hasAmmo()) 
        {
            double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 350);
            WindBurst bullet = new WindBurst(getHand().getTargetRotation(), d, nextIsSuper, getHolder(), getAttackUpgrade()==1?this:null);
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            getAmmo().useAmmo();
            nextIsSuper = false;
        }
    }
    public void fireUlt(){
        //hop 5 times, dealing damage on landing
        nextIsSuper = true;
        getAmmo().donateAmmo(1);
    }
    public void startJump(double rot, double dist, boolean isSuper){
        if(getAttackUpgrade()==1||isSuper){
            setLocked(true);
            double d = dist*(isSuper?7:2);
            getHolder().initiateJump(rot, d, isSuper?300:40);
        }
        if(getUltUpgrade()==1&&isSuper){
            getHolder().applyEffect(new SpeedPercentageEffect(1.5, 150, getHolder()));
            secondWind = 120;
        }
    }
    public void doLanding(){
        setLocked(false);//allow switching weapons
    }
    public void reload(double d){
        super.reload(d*(secondWind>0?2:1));
        if(secondWind>0){
            secondWind--;
        }
        if(nextIsSuper)updateAmmo(gunReloadTime+1);
    }
    public int getUlt(){
        return ult;
    }
    public AirPump(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(gunReloadTime, 1));
    }
    public String getName(){
        return "Air Pump";
    }
    public int getRarity(){
        return 4;
    }
    public BotGuide getBotGuide(){
        return new BotGuide();
    }
    public class BotGuide extends Weapon.BotGuide{
        public int getEffectiveRange(){
            return 350;
        }
        public int getUltEffectiveRange(){
            return 350;
        }
        public int getUltIdealRange(){
            return -1;
        }
        //TODO: requires number of nearby enemies, more when farther, just one when close
        public boolean shouldUseUlt(){
            return true;
        }
    }
}








