package com.karel.game.gridobjects.gridentities.zombies.jailbreak;

import com.karel.game.BombDropper;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.TickingTimeBomb;
import com.karel.game.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.cannon.CannonZombie;

import java.util.HashSet;

/**
 * Write a description of class JailBreakZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JailBreakZombie extends Zombie
{
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.pressurer, ZombieClass.penetrator};
    private static final int gunReloadTime = 150;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "jailbreakzareln.png";}
    private static double attackrange = 280, retreatrange = 300, bombrange = 40, crowbarrange = 110;
    private int calccannoncooldown = 0;
    private int strafecooldown = 0;
    private boolean cstrafedir = true;
    HashSet<CannonZombie> cannons = new HashSet<CannonZombie>();
    public CannonZombie nearestCannon;
    /**
     * Initilise this rocket.
     */
    public JailBreakZombie()
    {
        reloadDelayCount = 5;
        scaleTexture(50, 50);
        setSpeed(5);
        startHealth(500);
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
    public void behave()
    {
        if(calccannoncooldown<=0||(nearestCannon!=null&&nearestCannon.isDead())){
            calculateNearestCannon();
            calccannoncooldown = 30;
        }else{
            calccannoncooldown--;
        }
        reloadDelayCount+=getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
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
            if(distanceTo(nearestCannon)<bombrange){
                walk(monangle, -1);
            }else{
                if(distanceTo(nearestCannon)>bombrange+10)walk(monangle, 1);
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
    private void fire() 
    {
        if (canBomb()){
            for(int i = 0; i < 6; i++){
                BombDropper bd = new BombDropper(getRotation()+i*60, 50, 25, new TickingTimeBomb(this), this);
                addObjectHere(bd);
            }
            reloadDelayCount = 0;
        }
    }
    @Override
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 400;
    }
    
    public String getName(){
        return "Prison Break Zombie";
    }
}
