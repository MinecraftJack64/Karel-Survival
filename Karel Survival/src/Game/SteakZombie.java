package Game;
import greenfoot.*;
import java.util.List;

/**
 * Write a description of class SteakZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SteakZombie extends ShooterZombie
{
    private static final int gunReloadTime = 25;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.
    private GreenfootImage rocket = new GreenfootImage("steakzareln.png");    
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private AmmoManager ammo = new AmmoManager(30, 3, 3);
    private boolean hastarget = false;
    private boolean shouldheal = false;
    private boolean healself = false;
    private boolean mustbehurt = false;
    private GridEntity priority;
    private static double attackrange = 400, retreatrange = 600;
    /**
     * Initilise this rocket.
     */
    public SteakZombie()
    {
        reloadDelayCount = 5;
        rocket.scale(50, 50);
        setImage(rocket);
        setRotation(180);
        setSpeed(2);
        startHealth(500);
    }
    public SteakZombie(GridEntity target)
    {
        this();
        priority = target;
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        reloadDelayCount++;
        heal(this, 1);
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        if(reloadDelayCount>=gunReloadTime)ammo.reload();
        if(!hastarget){
            super.behave();
            getTarget();
            if(shouldheal&&healself){
                fire();
            }
            return;
        }
        if(hastarget&&distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else if(!hastarget&&distanceTo(getTarget())<retreatrange){
            walk(monangle, -1);
        }
        else if(shouldheal){
            fire();
            return;
        }
        if(shouldheal&&healself){
            fire();
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    public void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()&&ammo.hasAmmo()){
            double d = healself?3:distanceTo(getTarget());
            SteakDropper bullet = new SteakDropper(getRealRotation(), d, d/2, this);
            addObjectHere(bullet);
            reloadDelayCount = 0;
            ammo.useAmmo();
        }
    }
    //ovveride this
    public int getXP(){
        return 500;
    }
    
    public GridEntity getTarget(){
        hastarget = true;
        shouldheal = true;
        healself = false;
        GridEntity candidate = getNearestAlly(true);
        if(candidate==null){
            shouldheal = false;
            if(getHealth()<getMaxHealth()){//not at max health, no targets, then heal self
                shouldheal = true;
                healself = true;
            }
            candidate = getNearestAlly(false);
            if(candidate==null){
                hastarget = false;
            }
        }
        if(getHealth()<getMaxHealth()/2){//less than half health, heal self
            shouldheal = true;
            healself = true;
        }
        //healself = getHealth()<getMaxHealth()/2;
        return candidate==null?super.getTarget():candidate;
    }
    public GridEntity getNearestAlly(boolean mustbehurt){
        if(priority!=null&&!priority.isDead()){
            if(mustbehurt){
                if(priority.getHealth()<priority.getMaxHealth()){
                    return priority;//prioritize a patient
                }
            }else{
                return priority;
            }
        }
        
        this.mustbehurt = mustbehurt;
        return getNearestTarget();
    }
    public boolean isPotentialTarget(GridEntity e){
        return !(e==this||!isAlliedWith(e)||(mustbehurt&&!(e.getHealth()<e.getMaxHealth())));
    }
    public String getName(){
        return "Steak Zombie";
    }
}
