package com.karel.game.gridobjects.gridentities.zombies.russiandoll;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.ZombieClass;
import com.karel.game.effects.EffectID;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
/**
 * Write a description of class RussianDollZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RussianDollZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.tank};
    private double jumpcooldown;
    //Big: cooldown: 90, height: 150, distance: 350
    //Medium: cooldown: 60, height: 75, distance: 150
    //Small: cooldown: 30, height 25, distance: 50
    private GridEntity inside;
    public String getStaticTextureURL(){return "russianzareln.png";}
    private int size;
    public RussianDollZombie()
    {
        this(3, false);
    }
    public RussianDollZombie(int size, boolean isBaby){
        scaleTexture(10+size*20, 10+size*20);
        setRotation(0);
        setSpeed(1);
        startHealth(size*500-250);
        this.size = size;
        if(size>1){
            inside = new RussianDollZombie(size-1, true);
        }
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 800;
    }
    public void behave(){
        if(jumpcooldown<=0){
            if(distanceTo(getTarget())<getJumpDistance()&&inside!=null&&getHeight()==0){//spawn inside
                getWorld().addObject(inside, getX(), getY());
                inside = null;
                jumpcooldown = getJumpCooldown();
            }else if(canMove()&&isOnGround()){
                double d = Math.min(distanceTo(getTarget().getX(), getTarget().getY()), getJumpDistance()*getMultipliedSpeed());
                initiateJump(face(getTarget(), false), d, getJumpHeight());
                jumpcooldown = getJumpCooldown();
            }
        }
        jumpcooldown-=getReloadMultiplier();
    }
    public void doLanding(){
        if(!canAttack()){
            return;
        }
        explodeOn(getExplosionRange(), getDamage(), null);//smash explosion later
    }
    public void feast(){
        if(canAttack())mute(new EffectID(this));// prevent from processing landing explosion and wasting time.
        if(jumpcooldown<=0){
            if(distanceTo(getTarget())<getJumpDistance()&&inside!=null&&getHeight()==0){//spawn inside
                getWorld().addObject(inside, getX(), getY());
                inside = null;
                jumpcooldown = getJumpCooldown();
            }else if(distanceTo(getTarget().getX()+getFeastingRange(), getTarget().getY())>5){
                double d = Math.min(distanceTo(getTarget().getX()+getFeastingRange(), getTarget().getY()), getJumpDistance()*getMultipliedSpeed());
                initiateJump(face(getTarget(), false), d, getJumpHeight());
                jumpcooldown = getJumpCooldown();
            }
        }
        jumpcooldown--;
    }
    public int getJumpCooldown(){
        return size*50;
    }
    public int getJumpHeight(){
        return size*50-(size<3?25:0);
    }
    public int getJumpDistance(){
        return (int)(30*(Math.pow(2, size)-1));
    }
    public int getFeastingRange(){
        return 2*(10+size*20)/3;
    }
    public double getGravity(){
        return 4-size;
    }
    public int getDamage(){
        return size*100;
    }
    public int getExplosionRange(){
        return 40*size;
    }
    public void die(GridObject killer){
        if(inside!=null){
            getWorld().addObject(inside, getX(), getY());
            inside = null;
        }
        super.die(killer);
    }
    public boolean canBePulled(){
        return size<3;
    }
    public boolean isWall(){
        return size>=3;
    }
    @Override
    public boolean prioritizeTarget(){
        return size>=3;
    }
    
    public String getName(){
        return "Russian Doll Zombie";
    }
}
