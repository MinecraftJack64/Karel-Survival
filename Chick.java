import greenfoot.*;
import java.util.List;

/**
 * Write a description of class Chick here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Chick extends Pet
{

    private GreenfootImage rocket = new GreenfootImage("chickzareln.png");    
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private GridEntity myhive;
    private int ammo = 0;
    private double strength;
    /**
     * Initilise this rocket.
     */
    public Chick(double strength, GridEntity hive)
    {
        int scale = (int)(30*((strength-1)*0.5+1));
        rocket.scale(scale, scale);
        setImage(rocket);
        setRotation(180);
        setSpeed(3);
        startHealth((int)(100*strength));
        inherit(hive);
        myhive = hive;
        this.strength = strength;
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        ammo++;
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else{
            if(ammo>reloadtime&&canAttack()){
                attack();
                ammo = 0;
            }
        }
        
    }
    private int damage = 10;
    private static double speed = 2;
    private static double attackrange = 30;
    private static final int reloadtime = 5;
    public void attack(){
        if(isAggroTowards(getTarget()))damage(getTarget(), (int)(damage*strength));
    }
    public void die(GridObject killer){
        super.die(killer);
        try{getWorld().removeObject(this);}catch(Exception e){}
    }
    public GridEntity getTarget(){
        //return getWorld().player;
        GridEntity candidate = getNearestTarget();
        if(candidate == null){
            candidate = getWorld().getPlayer();
        }
        return candidate;//use this for now
    }
    public GridEntity getNearestTarget(){
        GridEntity res = null;
        double max = 0;
        for(GridEntity e: getWorld().allEntities){
            if(!isAggroTowards(e)||!e.canDetect()){
                continue;
            }
            if(distanceTo(e)<max||res==null){
                res = e;
                max = distanceTo(e);
            }
        }
        return res;
    }
    public void notifyDamage(GridEntity target, int amt){
        if(myhive!=null&&!myhive.isDead()){
            myhive.notifyDamage(target, amt);
        }else{
            super.notifyDamage(target, amt);
        }
    }
}
