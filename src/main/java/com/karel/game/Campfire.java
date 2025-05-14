package com.karel.game;
import java.util.List;

/**
 * Write a description of class Campfire here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Campfire extends Pet
{

    public static String getStaticTextureURL(){return "karelnOff.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    /**
     * Initilise this rocket.
     */
    public Campfire(GridEntity hive)
    {
        super(hive);
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
        explodeOn(150, (g)->{
            if(isAggroTowards(g))damage(g, 8);
            else if(isAlliedWith(g))heal(g, 4);
        }, null);
        heal(this, 1);
    }
    public void heal(int amt, GridObject source){
        super.heal(0, source);
    }
}
