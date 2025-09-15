package com.karel.game.gridobjects.gridentities.zombies.weedwacker;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.weapons.weedwacker.WeedwackerBlade;

/**
 * Write a description of class WeedwackerZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WeedwackerZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.pressurer};
    public String getStaticTextureURL(){return "weedwackerzareln.png";}
    private static final double attackrange = 128; // this is at the range of the weedwacker blade
    private static final double blindrange = 60; // target is too close to hit with the blade
    WeedwackerBlade bd;
    /**
     * Initilise this rocket.
     */
    public WeedwackerZombie()
    {
        setSpeed(2.5);
        startHealth(300);
    }
    public void behave(){
        if(bd == null){
            bd = new WeedwackerBlade(this);
            addObjectHere(bd);
            mount(bd, -90, 125);
            bd.immunize();
        }
        if(bd.isDead()){
            super.behave();
        }else{
            double ang = face(getTarget(), true);
            bd.spin(getReloadMultiplier());
            if(distanceTo(getTarget())>attackrange)walk(ang, 0.8);
            else if(distanceTo(getTarget())<blindrange)super.behave();
        }
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 250;
    }
    public String getName(){
        return "Weedwacker Zombie";
    }
}
