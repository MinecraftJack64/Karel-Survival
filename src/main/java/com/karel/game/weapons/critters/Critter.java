package com.karel.game.weapons.critters;

import com.karel.game.ExternalImmunityShield;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.HealCharge;
import com.karel.game.Pet;
import com.karel.game.effects.PowerPercentageEffect;
import com.karel.game.effects.StunEffect;
import com.karel.game.physics.Dasher;
import com.karel.game.physics.DasherDoer;
import com.karel.game.shields.ShieldID;
import com.raylib.Texture;

/**
 * Write a description of class Critter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Critter extends Pet implements ICritter
{

    public static Texture rocket = Greenfoot.loadTexture("rocket.png");
    public static Texture rocket2 = Greenfoot.loadTexture("rocketWithThrust.png");
    private LilCritters perch;
    private int ammo = 0;
    private Dasher dash;
    private boolean calledback = false;
    private int id;
    private int ult = 0;//250
    /**
     * Initilise this rocket.
     */
    public Critter(int id, LilCritters spawner, GridEntity hive)
    {
        super(hive);
        this.id = id;
        setImage(rocket);
        scaleTexture(20, 20);
        startHealth(100);
        setSpeed(4);
        if(hive!=null)inherit(hive);
        applyEffect(new StunEffect(15, this));
        perch = spawner;
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
        if(calledback){
            if(distanceTo(getSpawner())<8){
                reintegrate();
            }else if(dash!=null&&!dash.dash()){
                dash = null;
            }else if(ammo>reloadtime){
                dashhome();
                ammo = 0;
            }else{
                walk(monangle, 2);
            }
        }else if(dash!=null){
            if(!dash.dash()){
                dash = null;
            }
        }else if(ammo>reloadtime/2&&distanceTo(getTarget())>100){
            walk(monangle, 1);
        }
        else{
            if(ammo>reloadtime&&canAttack()&&isAggroTowards(getTarget())){
                attack();
                ammo = 0;
            }
        }
        
    }
    public void animate(){
        if(getImage()==rocket&&ult>=250){
            setImage(rocket2);
        }else if(getImage()==rocket2&&ult<250){
            setImage(rocket);
        }
    }
    public GridEntity getTarget(){
        if(calledback){
            return getSpawner();
        }else{
            return super.getTarget();
        }
    }
    private static final int reloadtime = 15;
    public void attack(){
        int damage;
        if(ult>=250){
            ult = 0;
            damage = 75;
        }else{
            damage = 50;
        }
        dash = new DasherDoer(getRotation(), 15, 7, 30, damage, this);
        dash.dash();
    }
    public void dashhome(){
        dash = new Dasher(getRotation(), 15, 7, this);
        dash.dash();
    }
    public boolean notifySpawnerDamage(){
        return false;
    }
    public void notifyDamage(GridEntity target, int amt){
        if(perch.getAttackUpgrade()==1)ult+=amt;
        super.notifyDamage(target, amt);
    }
    public void gadget(){
        heal(getMaxHealth()-getHealth(), this);
        addObjectHere(new HealCharge(0, getSpawner(), getSpawner().getMaxHealth()/10));
        applyEffect(new PowerPercentageEffect(1.5, 120, this));
    }
    public void toggleCallBack(){
        calledback = !calledback;
        if(calledback){
            applyShield(new ExternalImmunityShield(new ShieldID(this, "return immunity"), -1));
        }else{
            removeShield(new ShieldID(this, "return immunity"));
        }
    }
    public boolean isCalledBack(){
        return calledback;
    }
    public void reintegrate(){
        perch.regenerate(id);
        heal(getSpawner(), getSpawner().getMaxHealth()/7);
        die(this);
    }
    public String getPetID(){
        return "critters-critter";
    }
}
