package com.karel.game.gridobjects.gridentities.zombies.milky;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Write a description of class ShooterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MilkyZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.ranger, ZombieClass.melee, ZombieClass.whittler};
    private int shots = 20;
    private static final int gunReloadTime = 150;         // The minimum delay between firing the gun.
    private int explodeCooldown = 45;

    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    private int strafecooldown = 0;
    private boolean cstrafedir = true;

    private GridEntity target1, target2;
    private MilkCannon cannon1, cannon2;

    public String getStaticTextureURL(){return "gunzareln.png";}
    private static double attackrange = 350, retreatrange = 340, exploderange = 50;
    /**
     * Initilise this rocket.
     */
    public MilkyZombie()
    {
        reloadDelayCount = 150;
        setSpeed(2);
        startHealth(450);
    }
    public void behave()
    {
        getTargets();
        if(shots<=0){
            super.behave();
            return;
        }
        reloadDelayCount+=getReloadMultiplier();
        boolean fire = false;
        double monangle = face(target1, false);
        if(distanceTo(target1)>attackrange)walk(monangle, 0.5);
        else if(distanceTo(target1)<retreatrange){fire = true;walk(monangle, -0.5);}
        else{
            fire = true;
        }
        monangle = face(target2, false);
        if(distanceTo(target2)>attackrange)walk(monangle, 0.5);
        else if(distanceTo(target2)<retreatrange){fire = true;walk(monangle, -0.5);}
        else{
            fire = true;
        }
        face((target1.getX()+target2.getX())/2, (target1.getY()+target2.getY())/2, canMove());
        if(fire){
            fire();
        }
        if(distanceTo(target1)<exploderange||distanceTo(target2)<exploderange){
            explodeCooldown--;
            if(explodeCooldown<=0){
                explodeOn(50, shots*25);
                shots = 0;
            }
        }
        strafecooldown--;
        if(strafecooldown<=0){
            cstrafedir = !cstrafedir;
            strafecooldown = Greenfoot.getRandomNumber(100)+50;
        }
        if(cstrafedir){
            walk(monangle+90, 0.3);
        }else{
            walk(monangle-90, 0.3);
        }
    }
    public void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            cannon1.fire();
            cannon2.fire();
            shots--;
            reloadDelayCount = 0;
        }
    }

    public void notifyWorldAdd(){
        super.notifyWorldAdd();
        cannon1 = new MilkCannon(this);
        addObjectHere(cannon1);
        mount(cannon1, -180, 40);
        cannon2 = new MilkCannon(this);
        addObjectHere(cannon2);
        mount(cannon2, 0, 40);
    }
    public void notifyWorldRemove(){
        getWorld().removeObject(cannon1);
        getWorld().removeObject(cannon2);
        super.notifyWorldRemove();
    }
    public int defaultDamage(){
        return 80;
    }
    public int defaultReloadTime(){
        return 30;
    }
    public int defaultRange(){
        return 50;
    }

    public boolean isPotentialTarget(GridEntity other){
        return super.isPotentialTarget(other)&&target1!=other;
    }
    public void getTargets(){
        target1 = target2 = null;
        target1 = getTarget();
        target2 = getTarget();
        if(target2==null){
            target2 = target1;
            target1 = getTarget();
        }
        if(isInWorld()){
            cannon1.setTarget(target1);
            cannon2.setTarget(target2);
            if(target1==target2){
                cannon1.setDefaulting(true);
                cannon2.setDefaulting(true);
            }else{
                cannon1.setDefaulting(false);
                cannon2.setDefaulting(false);
            }
        }
    }

    @Override
    public int getXP(){
        return 300;
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Milky Zombie";
    }
    @Override
    public String getZombieID(){
        return "milky";
    }
}
