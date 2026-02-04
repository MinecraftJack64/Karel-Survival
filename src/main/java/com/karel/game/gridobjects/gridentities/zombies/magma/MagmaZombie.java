package com.karel.game.gridobjects.gridentities.zombies.magma;

import com.karel.game.Dasher;
import com.karel.game.FrictionDasher;
import com.karel.game.effects.KnockbackEffect;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
/**
 * Write a description of class RussianDollZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MagmaZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.tank};
    private double jumpcooldown;
    private Dasher dash;
    public String getStaticTextureURL(){return "arsonzareln.png";}
    public MagmaZombie()
    {
        startHealth(2000);
        setSpeed(0.25);
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
            if(jumpcooldown<=0){
                dash = new FrictionDasher(getRotation(), 11, 11, this);
                jumpcooldown = 150;
            }
            jumpcooldown-=getReloadMultiplier();
        }
        addObjectHere(new MagmaPuddle(this));
        super.behave();
    }
    
    public void attack(){
        getTarget().applyEffect(new KnockbackEffect(face(getTarget(), false), 175, 30, this));
        super.attack();
    }

    public int defaultDamage(){
        return 100;
    }

    public String getName(){
        return "Magma Zombie";
    }
    @Override
    public String getZombieID(){
        return "magma";
    }
}
