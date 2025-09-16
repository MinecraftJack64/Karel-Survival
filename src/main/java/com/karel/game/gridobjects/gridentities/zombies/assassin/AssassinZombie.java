package com.karel.game.gridobjects.gridentities.zombies.assassin;

import com.karel.game.Greenfoot;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Write a description of class AssassinZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AssassinZombie extends Zombie
{
    private ZombieClass[] classes = new ZombieClass[]{ZombieClass.assault, ZombieClass.controller, ZombieClass.whittler};
    private static final int gunReloadTime = 75;         // The minimum delay between firing the gun.
    private static final int stabReloadTime = 55;
    private double failcooldown = 0;//150
    private int inviscooldown = 40;//10 or 50-100 invisible, 20-40 visible
    private boolean isinvis = false;

    public String getStaticTextureURL(){return "assassinzareln.png";}
    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private static double attackrange = 820, retreatrange = 800, assassinrange = 75;
    /**
     * Initilise this rocket.
     */
    public AssassinZombie()
    {
        reloadDelayCount = 5;
        setRotation(180);
        setSpeed(4);
        startHealth(200);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behaveAsStealth()
    {
        reloadDelayCount+=getReloadMultiplier();
        if(failcooldown>0){
            failcooldown-=getReloadMultiplier();
        }
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else if(distanceTo(getTarget())<retreatrange){fire();walk(monangle, -1);}
        else{
            fire();
        }
    }
    public void behaveAsAssassin(){
        reloadDelayCount+=getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>20)walk(monangle, 2);
        else{
            stab();
        }
    }
    public void behave(){
        if(distanceTo(getTarget())<assassinrange){
            behaveAsAssassin();
        }else{
            behaveAsStealth();
        }
        if(inviscooldown<=0){
            isinvis = !isinvis;
            if(isinvis){
                inviscooldown = Greenfoot.getRandomNumber(50)+50;
            }else{
                inviscooldown = 40+Greenfoot.getRandomNumber(40);
            }
        }
        if(distanceTo(getTarget())<90){
            isinvis = false;
        }
        if(!isinvis&&getOpacity()<255){
            setOpacity(getOpacity()+51);
        }else if(isinvis&&getOpacity()>0){
            setOpacity(getOpacity()-51);
        }
        inviscooldown--;
    }
    @Override
    public boolean canDetect(){
        return getOpacity()>0;
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()&&failcooldown<=0){
            isinvis = false;
            ZDagger bullet = new ZDagger (getRotation(), this);
            addObjectHere(bullet);
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    public boolean prioritizeTarget(){
        return true;
    }
    public void stab(){
        if(reloadDelayCount>=stabReloadTime&&canAttack()){
            isinvis = false;
            failcooldown = 150;
            ZMeleeDagger z = new ZMeleeDagger(getRotation(), this);
            addObjectHere(z);
            reloadDelayCount = 0;
        }
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 700;
    }
    
    public String getName(){
        return "Assassin Zombie";
    }
}
