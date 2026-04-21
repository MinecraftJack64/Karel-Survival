package com.karel.game.weapons.mc_sword;

import com.karel.game.GridEntity;
import com.karel.game.Pet;
import com.karel.game.StrikeSurvivalShield;
import com.karel.game.shields.ShieldID;

/**
 * Write a description of class Chick here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Endermite extends Pet
{

    public String getStaticTextureURL(){return "chick.png";}
    private double ammo = 0;
    private int damage = 12;
    private static double attackrange = 30;
    private static final int reloadtime = 5;
    /**
     * Initilise this rocket.
     */
    public Endermite(GridEntity hive)
    {
        super(hive);
        int scale = 30;
        scaleTexture(scale, scale);
        setSpeed(3);
        startHealth(100);
        setTeam("allaggro");
        applyShield(0, new StrikeSurvivalShield(new ShieldID(this, "survive hit"), 2));
    }
    public void behave()
    {
        double monangle = face(getTarget(), canMove());
        ammo+= getReloadMultiplier();
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else{
            if(ammo>=reloadtime&&canAttack()){
                attack();
                ammo = 0;
            }
        }   
    }
    public void attack(){
        if(isAggroTowards(getTarget()))damage(getTarget(), damage);
    }
    public String getPetID(){
        return "mc_sword-endermite";
    }
}
