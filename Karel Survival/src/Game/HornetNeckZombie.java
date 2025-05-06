package Game;
import greenfoot.*;
import java.util.List;

/**
 * Write a description of class HornetNeckZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HornetNeckZombie extends Zombie
{
    private static final int gunReloadTime = 5;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    private GreenfootImage rocket = new GreenfootImage("hornetnestzareln.png");    
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private int bees = 20;
    private int attackphasecount = 0;
    private int attackphasecooldown = 0;
    private boolean attackphasesuper = !(Greenfoot.getRandomNumber(5)>0);
    /**
     * Initilise this rocket.
     */
    public HornetNeckZombie()
    {
        reloadDelayCount = gunReloadTime;
        rocket.scale(45, 45);
        setImage(rocket);
        setRotation(180);
        setSpeed(3);
        startHealth(400);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        reloadDelayCount++;
        super.behave();
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if(bees>0&&canAttack()&&reloadDelayCount>=gunReloadTime){
            Hornet bullet = new Hornet();
            getWorld().addObject (bullet, getRealX(), getRealY());
            bullet.applyShield(new PercentageShield(new ShieldID(bullet), 0.6, 30));
            bees--;
            reloadDelayCount = 0;
        }
    }
    
    public void hitIgnoreShield(int amt, double exp, GridObject source){
        fire();
        super.hitIgnoreShield(amt, exp, source);
    }
    //ovveride this
    public int getXP(){
        return 550;
    }
    
    public String getName(){
        return "Hornet-Neck Zombie";
    }
}
