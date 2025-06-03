package com.karel.game;
import java.util.List;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.weapons.ShieldID;

import java.util.HashSet;

/**
 * Write a description of class StuntZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StuntZombie extends Zombie implements LandingHandler
{
    private static final int gunReloadTime = 85;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "cannonzareln.png";}  
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private static double attackrange = 55, retreatrange = 65, bombrange = 20;
    private int calccannoncooldown = 0;
    private int strafecooldown = 0;
    private boolean cstrafedir = true;
    HashSet<CannonZombie> cannons = new HashSet<CannonZombie>();
    public CannonZombie nearestCannon;
    /**
     * Initilise this rocket.
     */
    public StuntZombie()
    {
        reloadDelayCount = 5;
        scaleTexture(35, 35);
        setSpeed(4);
        startHealth(150);
    }
    public void calculateNearestCannon(){
        cannons.clear();
        nearestCannon = null;
        for(GridEntity g: getWorld().allEntities()){
            if(g instanceof CannonZombie){
                CannonZombie c = (CannonZombie)g;
                cannons.add(c);
                if(nearestCannon==null||distanceTo(nearestCannon)>distanceTo(c)){
                    nearestCannon = c;
                }
            }
        }
    }
    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        if(calccannoncooldown<=0||(nearestCannon!=null&&nearestCannon.isDead())){
            calculateNearestCannon();
            calccannoncooldown = 30;
        }else{
            calccannoncooldown--;
        }
        reloadDelayCount++;
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        ammo++;
        if(distanceTo(getTarget())<retreatrange&&canBomb()){
            if(distanceTo(getTarget())<bombrange){
                fire();
            }else{
                walk(monangle, 1);
            }
        }
        else if(nearestCannon==null){
            if(distanceTo(getTarget())<attackrange){
                walk(monangle, -1);
            }else{
                if(distanceTo(getTarget())>attackrange+10)walk(monangle, 1);
            }
        }else{
            monangle = face(nearestCannon, canMove());
            if(distanceTo(nearestCannon)<attackrange){
                walk(monangle, -1);
            }else{
                if(distanceTo(nearestCannon)>attackrange+10)walk(monangle, 1);
            }
        }
        strafecooldown--;
        //if reloaded and close to you, run to you and throw bomb
        //otherwise, run to nearest cannon
        //if no nearest cannon, still run to you
        if(strafecooldown<=0){
            cstrafedir = !cstrafedir;
            strafecooldown = Greenfoot.getRandomNumber(40)+20;
        }
        if(cstrafedir){
            walk(monangle+90, 0.8);
        }else{
            walk(monangle-90, 0.8);
        }
    }
    public boolean canBomb(){
        return reloadDelayCount>=gunReloadTime&&canAttack();
    }
    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (canBomb()){
            ZSpear bullet = new ZSpear (getRealRotation(), this);
            getWorld().addObject (bullet, getRealX(), getRealY());
            //bullet.move ();
            reloadDelayCount = 0;
        }
    }
    public void doLanding(){
        explodeOn(50, 400);
        applyEffect(new SpeedPercentageEffect(1.5, 60, this));
        if(!hasShield(new ShieldID(this)))applyShield(new MetalShield(new ShieldID(this), 3));
    }
    //ovveride this
    public int getXP(){
        return 400;
    }
    
    public String getName(){
        return "Stunt Zombie";
    }
}
