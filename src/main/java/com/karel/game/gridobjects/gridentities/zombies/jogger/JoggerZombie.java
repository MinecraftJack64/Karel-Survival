package com.karel.game.gridobjects.gridentities.zombies.jogger;

import java.util.ArrayList;

import com.karel.game.Greenfoot;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.physics.Dasher;
import com.karel.game.shields.ShieldID;

/**
 * Write a description of class HornetNeckZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JoggerZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.pressurer, ZombieClass.meatshield};
    private Dasher dash;
    private int threshold;
    private boolean rushed = false;
    private static ArrayList<JoggerZombie> joggers = new ArrayList<>();

    public String getStaticTextureURL(){return "hornetnestzareln.png";}
    /**
     * Initilise this rocket.
     */
    public JoggerZombie()
    {
        setSpeed(3);
        startHealth(250);
        addEffectImmunities("speedMal");
        threshold = Greenfoot.getRandomNumber(getHealth());
        applyShield(new PropagatingStrikeSurvivalShield(new ShieldID(this), 1));
    }
    public boolean canBePulled(){
        return false;
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        if(dash!=null&&!dash.dash()){
            dash = null;
            setSpeed(4);
            propagateRush();
        }
        super.behave();
    }

    private void propagateRush() {
        for(JoggerZombie j: joggers){
            if(distanceTo(j)<300){
                if(Greenfoot.getRandomNumber(5)<4){
                    j.fire();
                }
            }
        }
    }
    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if(rushed)return;
        dash = new Dasher(getRotation(), 5, 20, this);
        rushed = true;
    }
    
    public void hitIgnoreShield(int amt, double exp, GridObject source){
        super.hitIgnoreShield(amt, exp, source);
        if(getHealth()<=threshold){
            fire();
        }
    }
    public void notifyWorldRemove(){
        super.notifyWorldRemove();
        joggers.remove(this);
    }
    public void notifyWorldAdd(){
        joggers.add(this);
        super.notifyWorldAdd();
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 200;
    }
    
    public String getName(){
        return "Jogger Zombie";
    }
    @Override
    public String getZombieID(){
        return "jogger";
    }
}
