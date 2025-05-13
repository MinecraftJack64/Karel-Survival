package com.karel.game;
import java.util.List;

/**
 * Write a description of class DemonZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DemonZombie extends SpawnableZombie
{

    public static String getStaticTextureURL(){return "demonzareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private GridEntity myhive;
    /**
     * Initilise this rocket.
     */
    public DemonZombie(GridEntity hive)
    {
        scaleTexture(20, 20);
        setSpeed(4.5);
        startHealth(25);
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
        //setRotation(getRotation()-1);
        super.behave();
        if(myhive.isDead()){
            heal(100, this);
        }
    }
    public void attack(){
        super.attack();
        getTarget().applyEffect(new PoisonEffect(2, 2, 2, this));
        GridObject t = getTarget();
        pullToBranch(t, t.getRealRotation()+90, distanceTo(t)-10);
        heal(this, 2);
    }
    
    public int defaultDamage(){
        return (int)Math.min(0, 20+(40-distanceTo(getTarget()))/2);
    }
    public int defaultReloadTime(){
        return (int)(distanceTo(getTarget())/2+10);
    }
    public int defaultRange(){
        return 40;
    }
    
    public void hit(int amt, GridObject source){
        super.heal(amt, source);
    }
    public void heal(int amt, GridObject source){
        super.hit(amt, source);
    }
    
    @Override
    public int getXP(){
        return 0;
    }
    
    public boolean canFly(){
        return true;
    }
    
    public String getName(){
        return "Demon Zombie";
    }
}
