package com.karel.game.gridobjects.gridentities.zombies.hardhat;

import com.karel.game.ArmorShield;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.shields.ShieldID;

/**
 * Write a description of class HardHatZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HardHatZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.tank, ZombieClass.support};
    private static final int gunReloadTime = 200;
    private double reloadDelayCount;
    public String getStaticTextureURL(){return "constructionzareln.png";}
    private boolean inShieldPhase = true;
    private ShieldID hhshieldid = new ShieldID(this, false, "hardhatzombie shield"), shieldid = new ShieldID(this);
    public HardHatZombie()
    {
        reloadDelayCount = gunReloadTime;
        setSpeed(1);
        startHealth(750);
        applyShield(new ArmorShield(shieldid, 750));
    }
    public void behave()
    {
        if(inShieldPhase&&!hasShield(shieldid)){
            inShieldPhase = false;
            setImage("hurtconstructionzareln.png");
        }
        reloadDelayCount+=getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        if(reloadDelayCount>=gunReloadTime&&canAttack()){
            attack();
            reloadDelayCount = 0;
        }
        if(distanceTo(getTarget())>200)
            walk(monangle, 1);
    }
    @Override
    public int getXP(){
        return 550;
    }

    public void attack(){
        explodeOn(225, (g)->{
            if(isAlliedWith(g)&&!g.hasShield(hhshieldid)&&!g.hasShield(shieldid)&&g.acceptExternalShields()){//
                int health = (int)(Math.min(300, Math.min(g.getMaxHealth(), g.getHealth()*2))*(0.5+0.5*getPercentHealth())*getPower());//shield is 300 health by default unless zombie has less max health or health
                g.applyShield(new ArmorShield(hhshieldid, health));
            }else if(isAggroTowards(g)){
                damage(g, 200);
            }
        });
        Sounds.play("hardhatattack");
    }

    @Override
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Hard Hat Zombie";
    }
    @Override
    public String getZombieID(){
        return "hardhat";
    }
}
