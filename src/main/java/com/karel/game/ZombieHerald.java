package com.karel.game;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;

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
 * Does not move, indicates that killing it will result in the boss spawning. Takes on the appearance of the boss
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ZombieHerald extends Zombie
{
    private static final int gunReloadTime = 5;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.
    public String getStaticTextureURL(){
        return "heraldzareln.png";
    }
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    //private int shieldhealth = 300;
    private int ammo = 0;
    private static double speed = 2.5;
    private static double attackrange = 30;
    private int damage = 10;
    //ShieldBar shieldBar;
    private boolean inShieldPhase = true;
    /**
     * Initilise this rocket.
     */
    public ZombieHerald()
    {
        reloadDelayCount = 5;
        setSpeed(0);
        startHealth(200);
    }
    //ovveride this
    public int getXP(){
        return 0;
    }
    public String getName(){
        return "Zombie Herald";
    }
    /*public void damage(int amt){
        if(shieldhealth>0&&amt>0){
            shieldhealth-=amt;
            shieldBar.setValue(shieldhealth);
            if(shieldhealth<=0){
                shieldhealth = 0;
                setImage(rocket2);
            }
        }else
            super.damage(amt);
    }*/
}
