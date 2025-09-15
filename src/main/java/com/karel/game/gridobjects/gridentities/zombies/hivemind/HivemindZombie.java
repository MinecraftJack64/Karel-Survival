package com.karel.game.gridobjects.gridentities.zombies.hivemind;

import com.karel.game.Greenfoot;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Write a description of class HivemindZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HivemindZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.spawner, ZombieClass.ranger};
    private static final int gunReloadTime = 150;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "hivezareln.png";}
    private static double standingrange = 500;
    private int bees = 40;
    private int attackphasecount = 0;
    private int attackphasecooldown = 0;
    private boolean attackphasesuper = !(Greenfoot.getRandomNumber(5)>0);
    /**
     * Initilise this rocket.
     */
    public HivemindZombie()
    {
        reloadDelayCount = gunReloadTime;
        setSpeed(2);
        startHealth(300);
    }
    public void behave()
    {
        reloadDelayCount += getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>standingrange)walk(monangle, 1);
        else if(bees<=0){
            super.behave();
        }
        else{
            fire();
        }
    }

    private void fire() 
    {
        if (attackphasecount>0/*finish started attacks*/||(reloadDelayCount>=gunReloadTime&&canAttack())){
            if(attackphasecooldown<=0&&bees>0){
                Zombee bullet = new Zombee(this);
                getWorld().addObject (bullet, getX(), getY());
                bees--;
                attackphasecount++;
                attackphasecooldown = 3;
            }
            attackphasecooldown--;
            if(attackphasecount>=4||(attackphasecount==3&&!attackphasesuper)||bees<=0){
                reloadDelayCount = 0;
                attackphasecount = 0;
                attackphasecooldown = 0;
                attackphasesuper = !(Greenfoot.getRandomNumber(5)>0);
            }
        }
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    public int getXP(){
        return 400;
    }
    
    public String getName(){
        return "Hivemind Zombie";
    }
}
