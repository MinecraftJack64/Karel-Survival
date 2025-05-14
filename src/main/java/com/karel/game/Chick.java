package com.karel.game;
import java.util.List;

/**
 * Write a description of class Chick here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Chick extends Pet
{

    public static String getStaticTextureURL(){return "chickzareln.png";}
    private int ammo = 0;
    private double strength;
    /**
     * Initilise this rocket.
     */
    public Chick(double strength, GridEntity hive)
    {
        super(hive);
        int scale = (int)(30*((strength-1)*0.5+1));
        scaleTexture(scale, scale);
        setSpeed(3);
        startHealth((int)(100*strength));
        inherit(hive);
        this.strength = strength;
        applyShield(0, new StrikeSurvivalShield(new ShieldID(this, "survive hit"), (int)(2*strength)));
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        ammo++;
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else{
            if(ammo>reloadtime&&canAttack()){
                attack();
                ammo = 0;
            }
        }
        
    }
    private int damage = 12;
    private static double speed = 2;
    private static double attackrange = 30;
    private static final int reloadtime = 5;
    public void attack(){
        if(isAggroTowards(getTarget()))damage(getTarget(), (int)(damage*strength));
    }
}
