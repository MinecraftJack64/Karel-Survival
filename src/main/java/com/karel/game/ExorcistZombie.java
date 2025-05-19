package com.karel.game;
import java.util.List;

/**
 * Write a description of class ExorcistZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ExorcistZombie extends Zombie
{
    private static final int gunReloadTime = 60;         // The minimum delay between firing the gun.

    private int ammocooldown;               // How long ago we fired the gun the last time.
    private int attackcooldown = 400;
    public String getStaticTextureURL(){return "exorcistzareln.png";} 
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private int ammoreload = 0;
    private boolean hastarget = false;
    private boolean shouldheal = false;
    private boolean healself = false;
    private boolean mustbehurt;
    private GridEntity priority;
    private static double attackrange = 250, retreatrange = 400;
    /**
     * Initilise this rocket.
     */
    public ExorcistZombie()
    {
        setSpeed(5.5);
        startHealth(400);
    }
    public ExorcistZombie(GridEntity target)
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
        double monangle = face(getTarget(), canMove()&&ammo==0);
        //setRotation(getRotation()-1);
        ammoreload++;
        if(!hastarget){
            if(attackcooldown>0){
                //die if survival mode
                attackcooldown--;
            }else{
                super.behave();
                getTarget();
                if(shouldheal&&healself){
                    startattack();
                }
                return;
            }
        }
        if(ammo>0){
            fire();
        }else if(hastarget&&distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else if(!hastarget&&distanceTo(getTarget())<retreatrange){
            walk(monangle, -1);
        }
        else if(shouldheal){
            startattack();
            return;
        }
        if(shouldheal&&healself){
            startattack();
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void startattack() 
    {
        if (ammoreload>=gunReloadTime&&canAttack()){
            ammo = 3;
            fire();
            ammoreload = 0;
        }
    }
    private void fire(){
        if (ammocooldown<=0&&ammo>0){
            ammo--;
            ZExorcistShot bullet = new ZExorcistShot (getRealRotation(), this, healself);
            addObjectHere(bullet);
            ammocooldown = 10;
        }else{
            ammocooldown--;
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
        if(candidate==null){//are there no allies who need healing?
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
        if(getHealth()<getMaxHealth()){//less than half health, heal self
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
        this.mustbehurt = mustbehurt;
        return getNearestTarget();
    }
    public boolean isPotentialTarget(GridEntity e){
        return !(e instanceof ExorcistZombie||e==this||!isAlliedWith(e)||(mustbehurt&&!(e.getHealth()<e.getMaxHealth())));
    }
    public String getName(){
        return "Exorcist Zombie";
    }
}
