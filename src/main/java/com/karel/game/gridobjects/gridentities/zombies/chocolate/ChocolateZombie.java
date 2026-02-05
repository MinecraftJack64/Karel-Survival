package com.karel.game.gridobjects.gridentities.zombies.chocolate;

import com.karel.game.ArmorShield;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.physics.Dasher;
import com.karel.game.physics.FrictionDasher;
import com.karel.game.shields.ShieldID;
/**
 * Write a description of class RussianDollZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ChocolateZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.tank, ZombieClass.midranger};
    private double jumpcooldown;
    private double shootcooldown;
    private Dasher dash;
    public String getStaticTextureURL(){return "russianzareln.png";}
    public ChocolateZombie()
    {
        startHealth(1000);
        applyShield(new ArmorShield(new ShieldID(this), 1000));
        setSpeed(1);
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 800;
    }
    public void behave(){
        face(getTarget(), true);
        if(dash!=null){
            if(!dash.dash()){
                dash = null;
            }
        }else{
            if(getMultipliedSpeed()>1.5&&distanceTo(getTarget())>30){
                walk(getRotation(), 1);
            }
            if(shootcooldown<=0&&distanceTo(getTarget())<300){
                if(distanceTo(getTarget())>75)addObjectHere(new ChocolateChunk(getRotation(), distanceTo(getTarget()), 80, this));
                else addObjectHere(new ShotChocolateChunk(getRotation(), this));
                shootcooldown = 150;
            }
            shootcooldown-=getReloadMultiplier();
            if(jumpcooldown<=0){
                dash = new FrictionDasher(getRotation(), 11, 11, this);
                jumpcooldown = 100;
            }
            jumpcooldown-=getReloadMultiplier();
        }
        if(!hasShield()){
            addObjectHere(new ChocolatePuddle(true, this));
            hit(1, this);
        }
    }
    
    public String getName(){
        return "Chocolate Zombie";
    }
    @Override
    public String getZombieID(){
        return "chocolate";
    }
}
