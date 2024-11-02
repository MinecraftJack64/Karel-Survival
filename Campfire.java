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
    /**
     * Initilise this rocket.
     */
    public Campfire(GridEntity hive)
    {
        super(hive);
        rocket.scale(30, 30);
        setImage(rocket);
        setRotation(180);
        startHealth(400);
        inherit(hive);
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
}
