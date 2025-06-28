package com.karel.game;
import java.util.List;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.weapons.EffectID;

/*
 * classes
 * fila-mint
 * reinforce-mint: shieldzombie
 * bombard-mint: explodingzombie
 * arma-mint: zombie that controls bombs that drop down
 * contain-mint
 * spear-mint
 * pepper-mint
 * enchant-mint
 * winter-mint
 * appease-mint: shooterzombie stays at a distance and shoots small low damage bullets at you, slower than normal
 * ail-mint: poisonzombie that occasionally lets out a cloud that poisons you and boosts nearby zombies and leaves behind a poison area on death. Will slow you if you are too close to it. Very slow and tanky
 * conceal-mint: ninjazombies remain invisible and wait for the perfect opportunity to strike. They run towards you and when they're next to you, they throw 9 ninja stars while quickly circling you 3 times. They then run away and wait again. They occasionally reveal their location for short intervals of time
 * enlighten-mint
 * enforce-mint: zombie
 */
/**
 * Write a description of class RussianDollZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RussianDollZombie extends Zombie
{
    private int jumpcooldown;
    //Big: cooldown: 90, height: 150, distance: 350
    //Medium: cooldown: 60, height: 75, distance: 150
    //Small: cooldown: 30, height 25, distance: 50
    private GridEntity inside;
    private boolean wasBaby;
    public String getStaticTextureURL(){return "russianzareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    //private int shieldhealth = 300;
    private int damage = 10;
    private int size;
    /**
     * Initilise this rocket.
     */
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
        wasBaby = isBaby;
        if(size>1){
            inside = new RussianDollZombie(size-1, true);
        }
    }
    //ovveride this
    public int getXP(){
        return 800;
    }
    public void behave(){
        if(jumpcooldown<=0){
            if(distanceTo(getTarget())<getJumpDistance()&&inside!=null&&getHeight()==0){//spawn inside
                getWorld().addObject(inside, getX(), getY());
                inside = null;
                jumpcooldown = getJumpCooldown();
            }else if(canMove()){
                double d = Math.min(distanceTo(getTarget().getX(), getTarget().getY()), getJumpDistance()*getMultipliedSpeed());
                initiateJump(face(getTarget(), false), d, getJumpHeight());
                jumpcooldown = getJumpCooldown();
            }
        }
        jumpcooldown--;
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
    public boolean wasInOriginalWave(){
        return !wasBaby;
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
