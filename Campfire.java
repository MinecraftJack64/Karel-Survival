import greenfoot.*;
import java.util.List;

/**
 * Write a description of class Campfire here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Campfire extends Pet
{

    private GreenfootImage rocket = new GreenfootImage("karelnOff.png");    
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private GridEntity myhive;
    /**
     * Initilise this rocket.
     */
    public Campfire(GridEntity hive)
    {
        rocket.scale(30, 30);
        setImage(rocket);
        setRotation(180);
        startHealth(400);
        inherit(hive);
        myhive = hive;
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        //setRotation(getRotation()-1);
        List<GridEntity> l = getObjectsInRange(150, GridEntity.class);
        for(GridEntity g:l){
            if(isAggroTowards(g))damage(g, 8);
            else if(isAlliedWith(g)&&!(g instanceof Campfire))heal(g, 4);
        }
        heal(this, 1);
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
            if(!isAggroTowards(e)){
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
