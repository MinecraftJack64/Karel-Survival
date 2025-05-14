package com.karel.game;
import java.util.List;

/**
 * Write a description of class Hornet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hornet extends SpawnableZombie
{

    public static String getStaticTextureURL(){return "hornetzareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    /**
     * Initilise this rocket.
     */
    public Hornet()
    {
        scaleTexture(20, 20);
        setSpeed(4.5);
        startHealth(50);
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
    }
    public void attack(){
        super.attack();
        if(Greenfoot.getRandomNumber(10)==0)getTarget().applyEffect(new PoisonEffect(3, 20, 3, this));
    }
    
    //ovveride this
    public int getXP(){
        return 0;
    }
    
    public boolean canFly(){
        return true;
    }
    
    public String getName(){
        return "Zombie Hornet";
    }
}
