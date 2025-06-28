package com.karel.game;
import java.util.List;

/**
 * Write a description of class Scarecrow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scarecrow extends Pet
{
    public String getStaticTextureURL(){return "chickzareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private static double attackrange = 600;
    private static final int reloadtime = 15;
    /**
     * Initilise this rocket.
     */
    public Scarecrow(GridEntity hive)
    {
        super(hive);
        int scale = 40;
        scaleTexture(scale, scale);
        startHealth(400);
        inherit(hive);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        ammo++;
        face(getTarget(), true);
        if(getTarget()!=getSpawner()&&distanceTo(getTarget())<attackrange&&ammo>reloadtime&&canAttack()){
            attack();
            ammo = (int)((attackrange-distanceTo(getTarget()))/attackrange*reloadtime); // shoot faster when target closer
        }
        hit(2, this);
    }
    public void attack(){
        ScarecrowStraw bullet = new ScarecrowStraw(getRotation(), this);
        addObjectHere(bullet);
    }
    public void die(GridObject killer){
        super.die(killer);
        try{getWorld().removeObject(this);}catch(Exception e){}
    }
}
