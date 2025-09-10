package com.karel.game.weapons.easterbasket;

import com.karel.game.Greenfoot;
import com.karel.game.ItemHolder;
import com.karel.game.LandingHandler;
import com.karel.game.SimpleAmmoManager;
import com.karel.game.weapons.Weapon;
/**
 * Write a description of class EasterBasket here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EasterBasket extends Weapon implements LandingHandler
{
    private static final int gunReloadTime = 30;
    private static final int ult = 1000;
    private int hopsLeft;
    private static final int defaultchance = 3;
    private int chance;
    public void fire(){
        if (getAmmo().hasAmmo()&&hopsLeft==0) 
        {
            boolean willspawn = Greenfoot.getRandomNumber(9-chance)<defaultchance;
            EasterEgg bullet = new EasterEgg(getHand().getTargetRotation(), willspawn, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            getAmmo().useAmmo();
            if(getAttackUpgrade()==1){
                if(willspawn){
                    chance = defaultchance;
                }else{
                    chance++;
                }
            }
        }
    }
    public void fireUlt(){
        //hop 5 times, dealing damage on landing
        if(hopsLeft!=0){
            hopsLeft+=5;
            return;
        }
        hopsLeft = 5;
        setLocked(true);
        startJump();
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
    public void startJump(){
        double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 275);
        getHolder().initiateJump(getHand().getTargetRotation(), d, 200);
        Chick spawn = new Chick(1.5, getHolder());
        getHolder().getWorld().addObject(spawn, getHolder().getX(), getHolder().getY());
    }
    public void doLanding(){
        hopsLeft--;
        getHolder().explodeOn(100, 200);
        if(!getHolder().isDead()&&hopsLeft>0&&getHolder().canMove()&&getHolder().canAttack()){
            startJump();
        }else{
            setLocked(false);//allow switching weapons
        }
    }
    public int getUlt(){
        return ult;
    }
    public EasterBasket(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(gunReloadTime, 1));
        chance = defaultchance;
    }
    public String getName(){
        return "Easter Basket";
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








