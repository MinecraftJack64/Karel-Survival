package com.karel.game.gridobjects.gridentities.zombies.doctor;

import com.karel.game.GridEntity;
import com.karel.game.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;

/**
 * Write a description of class DoctorZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DoctorZombie extends Zombie
{
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.support};
    private static final int gunReloadTime = 25;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    private int attackcooldown = 400;
    public String getStaticTextureURL(){return "doczareln.png";}
    private boolean hastarget = false;
    private boolean shouldheal = false;
    private boolean healself = false;
    private boolean mustbehurt = false;
    private GridEntity priority;
    private static double attackrange = 200, retreatrange = 400;
    public DoctorZombie()
    {
        reloadDelayCount = 5;
        setSpeed(4);
        startHealth(400);
    }
    public DoctorZombie(GridEntity target)
    {
        this();
        priority = target;
    }
    public void behave()
    {
        reloadDelayCount+=getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        if(!hastarget){
            if(attackcooldown>0){
                //die if survival mode
                attackcooldown--;
            }else{
                super.behave();
                getTarget();
                if(shouldheal&&healself){
                    fire();
                }
                return;
            }
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
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZHealShot bullet = new ZHealShot (getRotation(), this, healself);
            getWorld().addObject (bullet, getX(), getY());
            reloadDelayCount = 0;
        }
    }
    @Override
    public void feast(){
        behave();
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    public int getXP(){
        return 500;
    }
    public boolean isActive(){
        return super.isActive()&&!hastarget&&attackcooldown>0;
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
        if(candidate!=null)attackcooldown = 400;
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
    public boolean isPotentialTarget(GridEntity e){
        return !(e instanceof DoctorZombie||e==this||!isAlliedWith(e)||(mustbehurt&&!(e.getHealth()<e.getMaxHealth())));
    }
    public String getName(){
        return "Doctor Zombie";
    }
}
