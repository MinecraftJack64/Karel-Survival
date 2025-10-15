package com.karel.game.gridobjects.gridentities.zombies.cancer;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Write a description of class ShooterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CancerZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.ranger};
    private static final int gunReloadTime = 200;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    private int strafecooldown = 0;
    private boolean cstrafedir = true;
    private int shotCooldown = 0;//5
    private int shotsRemaining = 0;//4
    private double spawnChance = 0.5;

    public String getStaticTextureURL(){return "gunzareln.png";}
    private static double attackrange = 350, retreatrange = 340;
    /**
     * Initilise this rocket.
     */
    public CancerZombie()
    {
        reloadDelayCount = 150;
        setSpeed(5);
        startHealth(150);
    }
    public CancerZombie(Zombie parent){
        this();
        inherit(parent);
    }
    public void behave()
    {
        if(shotsRemaining>0){
            fire();
            return;
        }
        reloadDelayCount+=getReloadMultiplier()+spawnChance;
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else if(distanceTo(getTarget())<retreatrange){fire();walk(monangle, -1);}
        else{
            fire();
        }
        strafecooldown--;
        if(strafecooldown<=0){
            cstrafedir = !cstrafedir;
            strafecooldown = Greenfoot.getRandomNumber(40)+20;
        }
        if(cstrafedir){
            walk(monangle+90, 0.6);
        }else{
            walk(monangle-90, 0.6);
        }
    }
    public void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            spawnChance = 0;
            for(GridEntity g: getGEsInRange(500)){
                if(g.getEntityID().equals(getEntityID())){
                    spawnChance++;
                }
            }
            spawnChance = spawnChance>0?1/Math.pow(spawnChance, 2):1;
            shotsRemaining = 4;
            reloadDelayCount = 0;
        }
        if(shotsRemaining>0&&shotCooldown<=0){
            ZMetastasis bullet = new ZMetastasis (getRotation()+Math.sin(shotsRemaining*Math.PI/2)*15, this, spawnChance*Math.pow(0.5, 4-shotsRemaining));
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("gunshoot");
            shotsRemaining--;
            shotCooldown = 5;
        }else{
            shotCooldown--;
        }
    }
    //has a chance to avoid shots
    public void hitIgnoreShield(int amt, double exp, GridObject source){
        if(Greenfoot.getRandomNumber()>=spawnChance)super.hitIgnoreShield(amt, exp, source);
    }
    public void notifyHit(){
        if(isInWorld())addObjectHere(new CancerZombie(this));
    }
    @Override
    public int getXP(){
        return 300;
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Cancer Zombie";
    }
    @Override
    public String getZombieID(){
        return "cancer";
    }
}
