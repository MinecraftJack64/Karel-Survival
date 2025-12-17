package com.karel.game.weapons.farmer;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Pet;
import com.karel.game.shields.ShieldID;

/**
 * Write a description of class Scarecrow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scarecrow extends Pet
{
    public String getStaticTextureURL(){return "chick.png";}
    private int healthLoss = 1;
    private int increaseLossCooldown = 40; // 40
    private int ammo = 0;
    private static double attackrange = 600;
    private static final int reloadtime = 10;
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
    public int spawnImmunityLength(){
        return 200;
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        face(getTarget(), true);
        if(getTarget()!=getSpawner()&&distanceTo(getTarget())<attackrange&&ammo>reloadtime&&canAttack()){
            attack();
            ammo = (int)((attackrange-distanceTo(getTarget()))/attackrange*reloadtime); // shoot faster when target closer
        }
        if(!hasShield(new ShieldID(this, "spawn immunity"))){
            hit(healthLoss, this);
            ammo++;
            if(increaseLossCooldown>0){
                increaseLossCooldown--;
            }else{
                increaseLossCooldown = 40;
                healthLoss++;
            }
        }
    }
    public void attack(){
        ScarecrowStraw bullet = new ScarecrowStraw(getRotation(), this);
        addObjectHere(bullet);
    }
    public void die(GridObject killer){
        super.die(killer);
        try{getWorld().removeObject(this);}catch(Exception e){}
    }
    public String getPetID(){
        return "farmer-scarecrow";
    }
}
