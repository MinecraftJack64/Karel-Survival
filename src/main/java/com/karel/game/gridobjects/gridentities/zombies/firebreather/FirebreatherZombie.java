package com.karel.game.gridobjects.gridentities.zombies.firebreather;

import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.Greenfoot;

/**
 * Write a description of class FlamethrowerZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FirebreatherZombie extends Zombie
{
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.melee, ZombieClass.barrager};
    private static final int gunReloadTime = 3;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.

    private int nextNapalm = 30; // 25-35

    public String getStaticTextureURL(){return "flamezareln.png";}
    private static double attackrange = 150, retreatrange = 50;
    public FirebreatherZombie()
    {
        reloadDelayCount = 5;
        setSpeed(2);
        startHealth(400);
    }

    public void behave()
    {
        reloadDelayCount+= getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else if(distanceTo(getTarget())<retreatrange){fire();}
        else{
            walk(monangle, 0.5);
            fire();
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            for(int deg = -35; deg<=35; deg+=10){
                nextNapalm--;
                Flame mbullet = new Flame(getRotation()+deg, this);
                if(nextNapalm<=0){
                    nextNapalm = 25 + (int)(Greenfoot.getRandomNumber()*11);
                    mbullet = new NapalmFlame(getRotation()+deg, this);
                }
                addObjectHere(mbullet);
            }
            Sounds.play("flame");
            reloadDelayCount = 0;
        }
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    public int getXP(){
        return 900;
    }
    
    public String getName(){
        return "Firebreather Zombie";
    }
    @Override
    public String getZombieID(){
        return "firebreather";
    }
}
