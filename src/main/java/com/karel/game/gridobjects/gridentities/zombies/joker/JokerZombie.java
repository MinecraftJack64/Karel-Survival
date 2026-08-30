package com.karel.game.gridobjects.gridentities.zombies.joker;

import com.karel.game.Greenfoot;
import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.ZGenericBullet;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Write a description of class JokerZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JokerZombie extends Zombie
{
    private ZombieClass[] classes = new ZombieClass[]{ZombieClass.pressurer};
    private int counterct;
    public String getStaticTextureURL(){return "jokerzareln.png";}
    /**
     * Initilise this rocket.
     */
    public JokerZombie()
    {
        startHealth(400);
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 500;
    }
    public void hitIgnoreShield(int amt, double exp, GridObject source){
        if(counterct>0){
            super.hitIgnoreShield(amt, exp, source);
            counterct--;
        }else{
            counterct = Greenfoot.getRandomNumber(3);
            if(canAttack())doCounter(amt);
        }
    }
    public void doCounter(int dmg){
        switch(Greenfoot.getRandomNumber(4)){
            case 0://explode
                explodeOn(60, dmg);
                Sounds.play("explode");
            break;
            case 1://shoot
                addObjectHere(new ZGenericBullet(dmg*2, getRotation(), this));
            break;
            case 2://heal
                if(Greenfoot.getRandomNumber(3)>0)
                heal(this, dmg*2);
                else
                heal(this, dmg/2);
            break;
            case 3://teleport
                if(Greenfoot.getRandomNumber(3)>0){
                    return;
                }
                int degs = Greenfoot.getRandomNumber(360);
                double dist = distanceTo(getTarget());
                int retreat = 0;
                if(Greenfoot.getRandomNumber(4)==0){
                    if(getMaxHealth()*1.0/getHealth()>=0.5){
                        retreat = -1;
                    }else{
                        retreat = 1;
                    }
                }
                setLocation(getTarget().getX()+Math.cos(degs*Math.PI/180)*(dist+dmg*retreat), getTarget().getY()+Math.sin(degs*Math.PI/180)*(dist+dmg*retreat));
            break;
        }
    }
    public String getName(){
        return "Joker Zombie";
    }
    @Override
    public String getZombieID(){
        return "joker";
    }
}
