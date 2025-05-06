package Game;
import greenfoot.*;
import java.util.List;

/**
 * Write a description of class BreadBoxerZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BreadBoxerZombie extends Zombie
{
    private static final int gunReloadTime = 75;         // The minimum delay between firing the gun.
    private static final int punchReloadTime = 5;
    private int reloadDelayCount;               // How long ago we fired the gun the last time.
    private int punchDelay;
    private GreenfootImage rocket = new GreenfootImage("breadzareln.png");    
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private boolean punchdir;
    private static double attackrange = 125;
    private int checkdodge = 0;
    private int dodgereload = 160;//default is 50
    /**
     * Initilise this rocket.
     */
    public BreadBoxerZombie()
    {
        reloadDelayCount = 5;
        rocket.scale(45, 45);
        setImage(rocket);
        setRotation(180);
        setSpeed(4);
        startHealth(150);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        reloadDelayCount++;
        double monangle = face(getTarget(), canMove()&&ammo==0);
        checkDodge();
        //setRotation(getRotation()-1);
        if(distanceTo(getTarget())>attackrange&&ammo==0)walk(monangle, dodgereload>0?1:0.5);
        else{
            fire();
        }
    }
    public void checkDodge(){
        if(dodgereload<400)dodgereload++;
        if(dodgereload>=80){
            dodgereload--;
            if(checkdodge>0){
                checkdodge--;
            }else if(canMove()){
                checkdodge = 3;
                for(Projectile h:getGOsInRange(100, Projectile.class)){
                    if(h.isAggroTowards(this)){
                        initiateJump(getRealRotation()+(punchdir?-45:45), 100, 50);
                        applyShield(new PercentageShield(new ShieldID(this), 1, (int)(getPhysicsArc().getDuration())));
                        punchdir = !punchdir;
                        dodgereload-=80;
                        ammo = 0;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime){
            ammo = 6;
            reloadDelayCount = 0;
        }
        if(ammo>0){
            if(punchDelay<punchReloadTime){
                punchDelay++;
            }else if(canAttack()){
                PunchMissile bullet = new PunchMissile(getRealRotation()+(punchdir?-45:45), this);
                addObjectHere(bullet);
                punchdir = !punchdir;
                punchDelay = 0;
                ammo--;
            }
        }
    }
    //ovveride this
    public int getXP(){
        return 600;
    }
    
    public String getName(){
        return "Bread Boxer Zombie";
    }
}
