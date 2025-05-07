package com.karel.game;
import java.util.List;

/**
 * Write a description of class ArsonZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ArsonZombie extends Zombie
{
    private static final int attackCooldown = 200;         // The minimum delay between firing the gun.
    private static final int firingCooldown = 20;

    private int reloadDelayCount;               // How long ago we fired the gun the last time.
    private int firingDelay;
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private int nextAmmo = 1;
    private static double attackrange = 800, retreatrange = 700;
    /**
     * Initilise this rocket.
     */
    public ArsonZombie()
    {
        reloadDelayCount = 0;
        setTexture("arsonzareln");
        setRealRotation(180);
        setSpeed(2);
        startHealth(200);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        reloadDelayCount++;
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        firingDelay++;
        if(ammo>0)fire();
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else if(distanceTo(getTarget())<retreatrange){fire();walk(monangle, -1);}
        else{
            fire();
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if(ammo>0){
            if(firingDelay>=firingCooldown&&canAttack()){
                if(ammo==5){
                    superFire();
                    ammo = 1;
                }
                fireOne();
                ammo--;
                firingDelay = 0;
            }
        }
        else if (reloadDelayCount>=attackCooldown&&canAttack()){
            firingDelay = 0;
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
            ammo = nextAmmo;
            nextAmmo++;
            if(nextAmmo>5){
                nextAmmo = 1;
            }
        }
    }
    public void fireOne(){
        double d = distanceTo(getTarget());
        MolotovCocktail bullet = new MolotovCocktail (getRealRotation(), d, 400+d/4, this);
        getWorld().addObject (bullet, getRealX(), getRealY());
    }
    public void superFire(){
        for(int i = 0; i < 4; i++){double d = distanceTo(getTarget())+Greenfoot.getRandomNumber(60)-30;
        MolotovCocktail bullet = new MolotovCocktail (getRealRotation()+Greenfoot.getRandomNumber(30)-15, d, 100+d/2, this);
        getWorld().addObject (bullet, getRealX(), getRealY());}
    }
    //ovveride this
    public int getXP(){
        return 200;
    }
    
    public String getName(){
        return "Arson Zombie";
    }
}
