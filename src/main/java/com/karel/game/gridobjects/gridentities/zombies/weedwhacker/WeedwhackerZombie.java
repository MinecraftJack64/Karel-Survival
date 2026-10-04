package com.karel.game.gridobjects.gridentities.zombies.weedwhacker;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.weapons.weedwhacker.WeedwhackerBlade;

/**
 * Write a description of class WeedwhackerZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WeedwhackerZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.pressurer};
    public String getStaticTextureURL(){return "weedwhackerzareln.png";}
    private static final double attackrange = 128; // this is at the range of the weedwhacker blade
    private static final double blindrange = 60; // target is too close to hit with the blade
    WeedwhackerBlade bd;
    /**
     * Initilise this rocket.
     */
    public WeedwhackerZombie()
    {
        setSpeed(2.5);
        startHealth(300);
    }
    public void behave(){
        if(bd == null){
            bd = new WeedwhackerBlade(this);
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
        return "Weedwhacker Zombie";
    }
    @Override
    public String getZombieID(){
        return "weedwhacker";
    }
}
