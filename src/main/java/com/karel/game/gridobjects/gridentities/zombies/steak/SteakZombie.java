package com.karel.game.gridobjects.gridentities.zombies.steak;

import com.karel.game.ZombieClass;
import com.karel.game.AmmoManager;
import com.karel.game.GridEntity;
import com.karel.game.gridobjects.gridentities.zombies.shooter.ShooterZombie;

/**
 * Write a description of class SteakZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SteakZombie extends ShooterZombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.support, ZombieClass.ranger, ZombieClass.penetrator};
    private static final int gunReloadTime = 25;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    public String getStaticTextureURL(){return "steakzareln.png";}
    private AmmoManager ammo = new AmmoManager(30, 3, 3);
    private boolean hastarget = false;
    private boolean shouldheal = false;
    private boolean healself = false;
    private boolean mustbehurt = false;
    private GridEntity priority;
    private static double attackrange = 400, retreatrange = 600;
    /**
     * Initilise this rocket.
     */
    public SteakZombie()
    {
        reloadDelayCount = 5;
        scaleTexture(50, 50);
        setSpeed(2);
        startHealth(500);
    }
    public SteakZombie(GridEntity target)
    {
        this();
        priority = target;
    }

    public void behave()
    {
        reloadDelayCount+=getReloadMultiplier();
        heal(this, 1);
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        if(reloadDelayCount>=gunReloadTime)ammo.reload(getReloadMultiplier());
        if(!hastarget){
            super.behave();
            getTarget();
            if(shouldheal&&healself){
                fire();
            }
            return;
        }
        if(hastarget&&distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else if(!hastarget&&distanceTo(getTarget())<retreatrange){
            walk(monangle, -1);
        }
        else if(shouldheal){
            fire();
            return;
        }
        if(shouldheal&&healself){
            fire();
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    public void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()&&ammo.hasAmmo()){
            double d = healself?3:distanceTo(getTarget());
            SteakDropper bullet = new SteakDropper(getRotation(), d, d/2, this);
            addObjectHere(bullet);
            reloadDelayCount = 0;
            ammo.useAmmo();
        }
    }
    //ovveride this
    public int getXP(){
        return 500;
    }
    
    public GridEntity getTarget(){
        hastarget = true;
        shouldheal = true;
        healself = false;
        GridEntity candidate = getNearestAlly(true);
        if(candidate==null){
            shouldheal = false;
            if(getHealth()<getMaxHealth()){//not at max health, no targets, then heal self
                shouldheal = true;
                healself = true;
            }
            candidate = getNearestAlly(false);
            if(candidate==null){
                hastarget = false;
            }
        }
        if(getHealth()<getMaxHealth()/2){//less than half health, heal self
            shouldheal = true;
            healself = true;
        }
        //healself = getHealth()<getMaxHealth()/2;
        return candidate==null?super.getTarget():candidate;
    }
    public GridEntity getNearestAlly(boolean mustbehurt){
        if(priority!=null&&!priority.isDead()){
            if(mustbehurt){
                if(priority.getHealth()<priority.getMaxHealth()){
                    return priority;//prioritize a patient
                }
            }else{
                return priority;
            }
        }
        
        this.mustbehurt = mustbehurt;
        return getNearestTarget();
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    public boolean isPotentialTarget(GridEntity e){
        return !(e==this||!isAlliedWith(e)||(mustbehurt&&!(e.getHealth()<e.getMaxHealth())));
    }
    public String getName(){
        return "Steak Zombie";
    }
}
