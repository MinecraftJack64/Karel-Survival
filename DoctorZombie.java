import greenfoot.*;
import java.util.List;

/**
 * Write a description of class DoctorZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DoctorZombie extends Zombie
{
    private static final int gunReloadTime = 25;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.
    private int attackcooldown = 400;
    private GreenfootImage rocket = new GreenfootImage("doczareln.png");    
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private boolean hastarget = false;
    private boolean shouldheal = false;
    private boolean healself = false;
    private GridEntity priority;
    private static double attackrange = 250, retreatrange = 400;
    /**
     * Initilise this rocket.
     */
    public DoctorZombie()
    {
        reloadDelayCount = 5;
        rocket.scale(45, 45);
        setImage(rocket);
        setRotation(180);
        setSpeed(4);
        startHealth(400);
    }
    public DoctorZombie(GridEntity target)
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
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        ammo++;
        if(!hastarget){
            if(attackcooldown>0){
                //die if survival mode
                attackcooldown--;
            }else{
                super.behave();
                getTarget();
                if(shouldheal&&healself){
                    fire();
                }
                return;
            }
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
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZHealShot bullet = new ZHealShot (getRealRotation(), this, healself);
            getWorld().addObject (bullet, getRealX(), getRealY());
            reloadDelayCount = 0;
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
        if(candidate!=null)attackcooldown = 400;
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
        
        GridEntity res = null;
        double max = 0;
        for(GridEntity e: getWorld().allEntities()){
            if(e instanceof DoctorZombie||e==this||!isAlliedWith(e)||(mustbehurt&&!(e.getHealth()<e.getMaxHealth()))){
                continue;
            }
            if(distanceTo(e)<max||res==null){
                res = e;
                max = distanceTo(e);
            }
        }
        return res;
    }
    public String getName(){
        return "Doctor Zombie";
    }
}
