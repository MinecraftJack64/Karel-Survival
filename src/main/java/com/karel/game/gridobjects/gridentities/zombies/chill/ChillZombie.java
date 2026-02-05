package com.karel.game.gridobjects.gridentities.zombies.chill;

import java.util.Arrays;

import com.karel.game.GridEntity;
import com.karel.game.gridobjects.gridentities.zombies.SpawnableZombie;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.shooter.ShooterZombie;
import com.karel.game.trackers.AmmoManager;

/**
 * Write a description of class SteakZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ChillZombie extends ShooterZombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.support, ZombieClass.tactician, ZombieClass.controller};
    private static final int gunReloadTime = 60;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    public String getStaticTextureURL(){return "chillzareln.png";}
    private AmmoManager ammo = new AmmoManager(400, 3, 3);
    private GridEntity priority;
    private static double attackrange = 1000, standingrange = 600, retreatrange = 400;
    /**
     * Initilise this rocket.
     */
    public ChillZombie()
    {
        reloadDelayCount = 5;
        setSpeed(1);
        startHealth(500);
    }
    public ChillZombie(GridEntity target)
    {
        this();
        priority = target;
    }

    public void behave()
    {
        reloadDelayCount++;
        double monangle = face(super.getTarget(), canMove());
        if(distanceTo(getTarget())<attackrange){
            fire();
        }else{
            walk(face(getTarget(), canMove()), 1);
        }
        if(reloadDelayCount>=gunReloadTime)ammo.reload(getReloadMultiplier());
        else return;
        if(distanceTo(super.getTarget())>standingrange){
            walk(monangle, 1);
        }else if(distanceTo(super.getTarget())<retreatrange){
            walk(monangle, -1);
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    public void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()&&ammo.hasAmmo()){
            double d = distanceTo(getTarget());
            IceBolt bullet = new IceBolt(getRotation(), d, 150, this, getTarget());
            addObjectHere(bullet);
            reloadDelayCount = 0;
            ammo.useAmmo();
            if(isAggroTowards(getTarget()))ammo.donateAmmoBar(200); // reloads faster
        }
    }
    @Override
    public int getXP(){
        return 500;
    }
    
    public GridEntity getTarget(){
        if(priority!=null&&!priority.isDead()&&getPriority(priority)==0){
            return priority;
        }
        int bestP = -1;
        GridEntity best = null;
        for(GridEntity g: getWorld().allEntities()){
            int op = getPriority(g);
            if((g.canDetect()&&(isAlliedWith(g)||isAggroTowards(g)))&&(bestP<0&&getPriority(g)>=0||op<bestP||bestP!=0&&op==bestP&&distanceTo(best)>distanceTo(g))){
                best = g;
                bestP = op;
            }
        }
        return bestP==-1?super.getTarget():best;
    }
    public int getPriority(GridEntity targ){
        if(targ.getPercentHealth()<0.5||targ.getPercentHealth()<1&&targ.getHealth()<100){
            if(targ==this)return 1;
            else return 0;
        }else if(targ instanceof Zombie z&&Arrays.asList(z.getZombieClasses()).contains(ZombieClass.meatshield)&&!(targ instanceof SpawnableZombie)){
            return 2;
        }else if(isAggroTowards(targ)){
            return 3;
        }else if(isAlliedWith(targ)){
            return 4;
        }
        return -1;
    }
    @Override
    public void feast(){
        behave();
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    public String getName(){
        return "Chill Zombie";
    }
    @Override
    public String getZombieID(){
        return "chill";
    }
}
