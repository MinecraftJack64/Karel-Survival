package com.karel.game;
import java.util.List;

/**
 * Write a description of class Zombee here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Zombee extends SpawnableZombie
{

    public String getStaticTextureURL(){
        return "beezareln.png";
    }
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int damage = 50;
    private GridEntity myhive;
    /**
     * Initilise this rocket.
     */
    public Zombee(GridEntity hive)
    {
        this();
        inherit(hive);
        myhive = hive;
    }
    
    public Zombee(){
        scaleTexture(15, 15);
        setSpeed(4);
        startHealth(30);
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
        if(myhive!=null)matchTeam(myhive);
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>30)walk(monangle, 1);
        else{
            if(canAttack()){
                if(Greenfoot.getRandomNumber(10)==0)getTarget().applyEffect(new PoisonEffect(damage, 20, 3, this));
                else damage(getTarget(), damage);
                hit(damage, this);
            }
        }
        if(myhive!=null&&myhive.isDead()){
            hit(1, this);
        }
    }
    
    @Override
    public int getXP(){
        return 0;
    }
    @Override
    public GridEntity getTarget(){
        return myhive!=null?myhive.getTarget():getTarget();
    }
    
    public boolean canFly(){
        return true;
    }
    
    public String getName(){
        return "Zombee";
    }
}
