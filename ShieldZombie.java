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
 * Write a description of class ShieldZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShieldZombie extends Zombie
{
    private static final int gunReloadTime = 5;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    private GreenfootImage rocket = new GreenfootImage("shieldzareln.png");
    private GreenfootImage rocket2 = new GreenfootImage("zareln.png");
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    //private int shieldhealth = 300;
    private int ammo = 0;
    private static double speed = 2;
    private static double attackrange = 30;
    private int damage = 10;
    //ShieldBar shieldBar;
    private boolean inShieldPhase = true;
    /**
     * Initilise this rocket.
     */
    public ShieldZombie()
    {
        reloadDelayCount = 5;
        rocket.scale(45, 45);
        rocket2.scale(45, 45);
        setImage(rocket);
        setRotation(180);
        setSpeed(speed);
        startHealth(200);
        applyShield(new ArmorShield(this, 300));
        /*shieldBar = new ShieldBar(shieldhealth, 40, 5, this);
        KWorld.me.addObject(shieldBar, getRealX()*1.0, getRealY()-50);*/
    }
    //ovveride this
    public int getXP(){
        return 150;
    }
    public void behave(){
        if(inShieldPhase&&!hasShield()){
            inShieldPhase = false;
            setImage(rocket2);
        }
        super.behave();
    }
    
    public String getName(){
        return "Shield Zombie";
    }
}