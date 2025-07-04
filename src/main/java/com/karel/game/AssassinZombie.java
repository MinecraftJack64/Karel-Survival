package com.karel.game;
import java.util.List;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;

/**
 * Write a description of class AssassinZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AssassinZombie extends Zombie
{
    private static final int gunReloadTime = 75;         // The minimum delay between firing the gun.
    private static final int stabReloadTime = 55;
    private int failcooldown = 0;//150

    private int reloadDelayCount;               // How long ago we fired the gun the last time.
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private static double attackrange = 820, retreatrange = 800, assassinrange = 75;
    /**
     * Initilise this rocket.
     */
    public AssassinZombie()
    {
        reloadDelayCount = 5;
        setTexture("assassinzareln.png");
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
        reloadDelayCount++;
        if(failcooldown>0){
            failcooldown--;
        }
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else if(distanceTo(getTarget())<retreatrange){fire();walk(monangle, -1);}
        else{
            fire();
        }
    }
    public void behaveAsAssassin(){
        reloadDelayCount++;
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
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()&&failcooldown<=0){
            ZDagger bullet = new ZDagger (getRotation(), this);
            addObjectHere(bullet);
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    public void stab(){
        if(reloadDelayCount>=stabReloadTime&&canAttack()){
            failcooldown = 150;
            ZMeleeDagger z = new ZMeleeDagger(getRotation(), this);
            addObjectHere(z);
            reloadDelayCount = 0;
        }
    }
    //ovveride this
    public int getXP(){
        return 700;
    }
    
    public String getName(){
        return "Assassin Zombie";
    }
}
