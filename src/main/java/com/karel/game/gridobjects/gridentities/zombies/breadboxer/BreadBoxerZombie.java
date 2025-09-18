package com.karel.game.gridobjects.gridentities.zombies.breadboxer;

import com.karel.game.PercentageShield;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.gridobjects.hitters.Projectile;
import com.karel.game.weapons.ShieldID;

/**
 * Write a description of class BreadBoxerZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BreadBoxerZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.pressurer, ZombieClass.melee};
    private static final int gunReloadTime = 75;         // The minimum delay between firing the gun.
    private static final int punchReloadTime = 5;
    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    private int punchDelay;
    public String getStaticTextureURL(){return "breadzareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private boolean punchdir;
    private static double attackrange = 125;
    private int checkdodge = 0;
    private double dodgereload = 160;//default is 50
    /**
     * Initilise this rocket.
     */
    public BreadBoxerZombie()
    {
        reloadDelayCount = 5;
        setSpeed(4);
        startHealth(150);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        reloadDelayCount+=getReloadMultiplier();
        if(!isOnGround())return;
        double monangle = face(getTarget(), canMove()&&ammo==0);
        checkDodge();
        //setRotation(getRotation()-1);
        if(distanceTo(getTarget())>attackrange&&ammo==0)walk(monangle, dodgereload>0?1:0.5);
        else{
            fire();
        }
    }
    //Stores up to 5 dodges, each taking 80 ticks to charge
    //Dodges towards the left or right of the target, alternating each time
    //Checks every 3 ticks while available
    public void checkDodge(){
        if(dodgereload<400)dodgereload+=getReloadMultiplier();
        if(dodgereload>=80){
            dodgereload--;
            if(checkdodge>0){
                checkdodge--;
            }else if(canMove()){
                checkdodge = 3;
                for(Projectile h:getGOsInRange(100, Projectile.class)){
                    if(h.isAggroTowards(this)){
                        initiateJump(getRotation()+(punchdir?-45:45), 100, 50);
                        applyShield(new PercentageShield(new ShieldID(this), 1, (int)(getPhysicsArc().getDuration())));
                        punchdir = !punchdir;
                        dodgereload-=80;
                        ammo = 0;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime){
            ammo = 6;
            reloadDelayCount = 0;
        }
        if(ammo>0){
            if(punchDelay<punchReloadTime){
                punchDelay++;
            }else if(canAttack()){
                PunchMissile bullet = new PunchMissile(getRotation()+(punchdir?-45:45), this);
                addObjectHere(bullet);
                punchdir = !punchdir;
                punchDelay = 0;
                ammo--;
            }
        }
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 600;
    }
    
    public String getName(){
        return "Bread Boxer Zombie";
    }
    @Override
    public String getZombieID(){
        return "breadboxer";
    }
}
