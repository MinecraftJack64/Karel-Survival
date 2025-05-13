package com.karel.game;
import java.util.List;

/**
 * Write a description of class DemonZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EvilSpiritZombie extends SpawnableZombie
{

    public static String getStaticTextureURL(){return "spiritzareln.png";}  
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int damage = 50;
    private GridEntity myhive;
    /**
     * Initilise this rocket.
     */
    public EvilSpiritZombie(GridEntity hive)
    {
        scaleTexture(15, 15);
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
        //matchTeam(myhive);
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        if(distanceTo(getTarget())>30)walk(monangle, 1);
        else{
            if(canAttack()){
                //getTarget().applyeffect();
                hit(damage, this);//intended
            }
        }
        if(myhive.isDead()){
            hit(1, this);
        }
    }
    
    //ovveride this
    public int getXP(){
        return 0;
    }
    
    
    public String getName(){
        return "Evil Spirit Zombie";
    }
}
