package com.karel.game;
import java.util.List;

/**
 * Write a description of class ZombieChick here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ZombieChick extends SpawnableZombie
{

    public String getStaticTextureURL(){
        return "chickzareln.png";
    }
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private GridEntity myhive;
    /**
     * Initilise this rocket.
     */
    public ZombieChick(GridObject source, GridEntity hive)
    {
        scaleTexture(30, 30);
        setSpeed(3);
        startHealth(100);
        myhive = hive;
        inherit(source);
    }
    
    public ZombieChick(GridEntity hive)
    {
        scaleTexture(30, 30);
        setSpeed(3);
        startHealth(100);
        myhive = hive;
        inherit(hive);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        //setRotation(getRotation()-1);
        super.behave();
        if(myhive.isDead()){
            hit(1, this);
        }
    }
    
    //ovveride this
    public int getXP(){
        return 10;
    }
    
    public String getName(){
        return "Zombie Chick";
    }
}
