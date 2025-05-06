package Game;
import greenfoot.*;
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
 * Write a description of class SplitterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SplitterZombie extends Zombie
{
    private static final int gunReloadTime = 5;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.
    private GreenfootImage rocket = new GreenfootImage("splitzareln.png");
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    //private int shieldhealth = 300;
    private int ammo = 0;
    private static double speed = 2.5;
    private static double attackrange = 30;
    private int damage = 10;
    private int waittime = 200;
    //ShieldBar shieldBar;
    private boolean inShieldPhase = true;
    /**
     * Initilise this rocket.
     */
    public SplitterZombie()
    {
        reloadDelayCount = 5;
        rocket.scale(45, 45);
        setImage(rocket);
        setRotation(180);
    }
    public void die(GridObject cause){
        LightZombie l = new LightZombie(this);
        HeavyZombie h = new HeavyZombie(this);
        addObjectHere(l);
        addObjectHere(h);
        super.die(cause);
    }
    //ovveride this
    public int getXP(){
        return 0;
    }
    public String getName(){
        return "Splitter Zombie";
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
