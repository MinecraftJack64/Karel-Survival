package com.karel.game.gridobjects.gridentities.zombies.rocket;
import com.karel.game.Greenfoot;
import com.karel.game.Rocket;
import com.karel.game.SpeedPercentageEffect;
import com.karel.game.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
/**
 * Write a description of class RocketZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RocketZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.assault, ZombieClass.meatshield};
    private double cooldown = 0, flightpoint = Greenfoot.getRandomNumber(50)+75;//Range from 75 150               // How long ago we fired the gun the last time.
    private String rocket = "rocketzareln.png";
    private String rocket2 = "zareln.png";
    //ShieldBar shieldBar;
    private int rocketPhase = 0;//0 - start flying, 1 - currently flying, 2 - done flying
    private Rocket ride;
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
            cooldown+=getReloadMultiplier();
        }else if(cooldown<flightpoint){
            cooldown++;
        }else{
            if(rocketPhase == 0){
                if(canAttack())fly();
            }else if(rocketPhase==1&&ride.hasLanded()){
                ride = null;
                rocketPhase = 2;
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
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public boolean prioritizeTarget(){
        return rocketPhase<=1;
    }
    
    public String getName(){
        return "Rocket Zombie";
    }
}
