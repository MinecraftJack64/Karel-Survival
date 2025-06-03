package com.karel.game;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.raylib.Texture;
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
 * Write a description of class RocketZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RocketZombie extends Zombie
{
    private int cooldown = 0, flightpoint = Greenfoot.getRandomNumber(50)+75;//Range from 75 150               // How long ago we fired the gun the last time.
    private Texture rocket = Greenfoot.loadTexture("rocketzareln.png");
    private Texture rocket2 = Greenfoot.loadTexture("zareln.png");
    //ShieldBar shieldBar;
    private int rocketPhase = 0;//0 - start flying, 1 - currently flying, 2 - done flying
    private Rocket ride;
    private Target targetsymbol;
    /**
     * Initilise this rocket.
     */
    public RocketZombie()
    {
        setImage(rocket);
        setSpeed(2);
        startHealth(600);
    }
    public RocketZombie(int startcooldown, int flightcooldown){
        this();
        cooldown = startcooldown;
        flightpoint = flightcooldown;
    }
    public void behave(){
        if(cooldown<50){
            super.behave();
            cooldown++;
        }else if(cooldown<flightpoint){
            cooldown++;
        }else{
            if(rocketPhase == 0){
                if(canAttack())fly();
            }else if(rocketPhase==1&&ride.hasLanded()){
                ride = null;
                rocketPhase = 2;
                targetsymbol = null;
                setImage(rocket2);
                applyEffect(new SpeedPercentageEffect(1.5, 150, this));
            }else{
                super.behave();
            }
        }
    }
    public void fly(){
        double x = getTarget().getRealX(), y = getTarget().getRealY();
        double d = distanceTo(x, y);
        ride = new Rocket(getAngle(x, y)+90, d, 2000, this, this);
        getWorld().addObject(ride, getRealX(), getRealY());
        ride.applyTarget(getTarget().getRealX(), getTarget().getRealY());
        rocketPhase = 1;
    }
    @Override
    public boolean prioritizeTarget(){
        return rocketPhase<=1;
    }
    
    public String getName(){
        return "Rocket Zombie";
    }
}
