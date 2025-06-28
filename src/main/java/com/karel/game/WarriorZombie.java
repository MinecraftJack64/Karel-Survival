package com.karel.game;
import java.util.List;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;

/**
 * Write a description of class WarriorZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WarriorZombie extends Zombie
{
    private static final int gunReloadTime = 85;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "warriorzareln.png";} 
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private static double attackrange = 310, retreatrange = 300;
    private int chargewaitrange = 600;
    private int chargecooldown = 0;
    private int phase;//0 is rest, 1 is charging
    private int strafecooldown = 0;
    private boolean cstrafedir = true;
    /**
     * Initilise this rocket.
     */
    public WarriorZombie()
    {
        reloadDelayCount = 5;
        setSpeed(4);
        startHealth(400);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behaveAsRanger()
    {
        reloadDelayCount++;
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        ammo++;
        strafecooldown--;
        if(strafecooldown<=0){
            cstrafedir = !cstrafedir;
            strafecooldown = Greenfoot.getRandomNumber(40)+20;
        }
        if(cstrafedir){
            walk(monangle+90, 0.8);
        }else{
            walk(monangle-90, 0.8);
        }
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);//HOP
        else if(distanceTo(getTarget())<retreatrange){fire();walk(monangle, -1);}
        else{
            fire();
        }
    }
    public void behave(){
        if(getPercentHealth()<0.75){
            setSpeed(4);
            behaveAsRanger();
            phase = 0;
        }else{
            behaveAsAssault();
        }
    }
    public void behaveAsAssault(){
        if(phase==0){//retreating
            chargecooldown--;
            if(chargecooldown<=0){
                phase = 1;
                chargecooldown = 125;
            }
            face(getTarget(), canMove());
            if(distanceTo(getTarget())<chargewaitrange){fire();walk(getRotation(), -1);}
        }else if(phase==1){//charging
            walk(getRotation(), 2);
            chargecooldown--;
            if(distanceTo(getTarget())<100){
                if(distanceTo(getTarget())<50)if(canAttack())damage(getTarget(), 50);
                phase = 2;
                setSpeed(7);
            }else if(chargecooldown<=0){
                phase = 0;
                chargecooldown = Greenfoot.getRandomNumber(30)+60;
            }
        }else{
            super.behave();
        }
    }
    public void attack(){
        damage(getTarget(), 30);
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZSpear bullet = new ZSpear (getRotation(), this);
            getWorld().addObject (bullet, getX(), getY());
            //bullet.move ();
            reloadDelayCount = 0;
        }
    }
    //ovveride this
    public int getXP(){
        return 400;
    }
    
    public String getName(){
        return "Warrior Zombie";
    }
}
