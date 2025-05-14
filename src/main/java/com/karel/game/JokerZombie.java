package com.karel.game;
import java.util.List;

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
 * Write a description of class JokerZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JokerZombie extends Zombie
{
    private int counterct;
    public static String getStaticTextureURL(){return "russianzareln.png";}
    /**
     * Initilise this rocket.
     */
    public JokerZombie()
    {
        startHealth(400);
    }
    //ovveride this
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
                addObjectHere(new ZGenericBullet(dmg*2, getRealRotation(), this));
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
                setRealLocation(getTarget().getRealX()+Math.cos(degs*Math.PI/180)*(dist+dmg*retreat), getTarget().getRealY()+Math.sin(degs*Math.PI/180)*(dist+dmg*retreat));
            break;
        }
    }
    public String getName(){
        return "Joker Zombie";
    }
}
