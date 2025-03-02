import greenfoot.*;
import java.util.List;

/**
 * Write a description of class Zombee here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Zombee extends SpawnableZombie
{

    private GreenfootImage rocket = new GreenfootImage("beezareln.png");    
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int damage = 50;
    private GridEntity myhive;
    /**
     * Initilise this rocket.
     */
    public Zombee(GridEntity hive)
    {
        rocket.scale(15, 15);
        setImage(rocket);
        setRotation(180);
        setSpeed(4);
        startHealth(30);
        setTeam(hive.getTeam());
        myhive = hive;
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        if(getRealHeight()==0){
            setRealHeight(1);
        }
        matchTeam(myhive);
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        if(distanceTo(getTarget())>30)walk(monangle, 1);
        else{
            if(canAttack()){
                if(Greenfoot.getRandomNumber(10)==0)getTarget().applyEffect(new PoisonEffect(damage, 20, 3, this));
                else damage(getTarget(), damage);
                hit(damage, this);
            }
        }
        if(myhive.isDead()){
            hit(1, this);
        }
    }
    
    @Override
    public int getXP(){
        return 0;
    }
    @Override
    public GridEntity getTarget(){
        return myhive.getTarget();
    }
    
    public boolean canFly(){
        return true;
    }
    
    public String getName(){
        return "Zombee";
    }
}
