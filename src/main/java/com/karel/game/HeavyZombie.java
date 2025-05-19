package com.karel.game;
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
 * Write a description of class HeavyZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HeavyZombie extends SpawnableZombie
{
    public String getStaticTextureURL(){return "heavyzareln.png";}
    //ShieldBar shieldBar;
    /**
     * Initilise this rocket.
     */
    public HeavyZombie(GridObject parent)
    {
        this();
        inherit(parent);
    }
    public HeavyZombie(){
        setSpeed(0.2);
        startHealth(4000);
    }
    //ovveride this
    public int getXP(){
        return 150;
    }
    public String getName(){
        return "Heavy Zombie";
    }
}
