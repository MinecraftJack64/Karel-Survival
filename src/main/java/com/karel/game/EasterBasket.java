package com.karel.game;
import java.util.List;
/**
 * Write a description of class EasterBasket here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EasterBasket extends Weapon implements LandingHandler
{
    private static final int gunReloadTime = 20;
    private int reloadDelayCount;
    private static final int ult = 1000;
    private int hopsLeft;
    private static final int defaultchance = 3;
    private int chance;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime&&hopsLeft==0) 
        {
            boolean willspawn = Greenfoot.getRandomNumber(9)<defaultchance;
            EasterEgg bullet = new EasterEgg(getHand().getTargetRotation(), willspawn, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            reloadDelayCount = 0;
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
    public void startJump(){
        double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 275);
        getHolder().initiateJump(getHand().getTargetRotation(), d, 200);
        Chick spawn = new Chick(1.5, getHolder());
        getHolder().getWorld().addObject(spawn, getHolder().getRealX(), getHolder().getRealY());
    }
    public void doLanding(){
        hopsLeft--;
        List<GridEntity> l = getHolder().getGEsInRange(100);
        for(GridEntity g:l){
            if(getHolder().isAggroTowards(g))getHolder().damage(g, 200);
        }
        if(!getHolder().isDead()&&hopsLeft>0&&getHolder().canMove()&&getHolder().canAttack()){
            startJump();
        }else{
            setLocked(false);//allow switching weapons
        }
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public int getUlt(){
        return ult;
    }
    public EasterBasket(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;//
        chance = defaultchance;
    }
    public void equip(){
        super.equip();
        newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Easter Basket";
    }
    public int getRarity(){
        return 7;
    }
}








