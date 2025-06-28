package com.karel.game.gridobjects.gridentities.zombies.laser;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.ZombieClass;

/**
 * Write a description of class LaserZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LaserZombie extends Zombie
{
    private static final ZombieClass[] classes = new ZombieClass[]{com.karel.game.ZombieClass.midranger};
    private static final int gunReloadTime = 75;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "laserzareln.png";}
    private static double attackrange = 320, attackrange2 = 150;
    private static double retreatrange = 300, pointblankrange = 50;
    public LaserZombie()
    {
        reloadDelayCount = 5;
        setSpeed(3.2);
        startHealth(150);
    }
    public void behave()
    {
        reloadDelayCount+=getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        double d = distanceTo(getTarget());
        if(d>attackrange||d<attackrange2&&d>pointblankrange)walk(monangle, 1);
        else if(d<retreatrange&&d>pointblankrange)walk(monangle, -1);
        else{
            fire();
        }
    }
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZLaser bullet = new ZLaser (getRotation()+10, this);
            getWorld().addObject (bullet, getX(), getY());
            ZLaser bullet2 = new ZLaser (getRotation()-10, this);
            getWorld().addObject (bullet2, getX(), getY());
            ZLaser bullet3 = new ZLaser (getRotation()+15, this);
            getWorld().addObject (bullet3, getX(), getY());
            ZLaser bullet4 = new ZLaser (getRotation()-15, this);
            getWorld().addObject (bullet4, getX(), getY());
            reloadDelayCount = 0;
        }
    }
    @Override
    public int getXP(){
        return 600;
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    public String getName(){
        return "Laser Zombie";
    }
}
