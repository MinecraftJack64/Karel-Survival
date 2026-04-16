package com.karel.game.weapons.morphingstone;

import com.karel.game.GridEntity;
import com.karel.game.Pet;
import com.karel.game.trackers.AmmoManager;

/**
 * Used by FlashDrive
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GuardianBeast extends Pet
{   
    private int startCooldown = 45;
    private int life, maxlife;
    private static int attackrange = 100, reloadTime = 30, ultrange = 500, ult = 600;
    private boolean upgraded;
    private int ultCharge;
    private GridEntity cached;
    private int reloadDelay = 0;
    private AmmoManager ammo = new AmmoManager(60, 3, 3);
    /**
     * Initilise this rocket.
     */
    public GuardianBeast(GridEntity hive, GridEntity cache, boolean upgraded)
    {
        super(hive);
        this.upgraded = upgraded;
        maxlife = life = upgraded?550:450;
        setSpeed(4);
        if(cache.trap()){
            cached = cache;
            cached.getWorld().removeObject(cached);
            startHealth(Math.min(cache.getMaxHealth()*2, 2000));
        }else{
            startHealth(800);
        }
        inherit(hive);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        if(startCooldown>0){
            startCooldown--;
            if(startCooldown==30){
                knockBackOnEnemies(200, 50);
            }
            return;
        }
        reloadDelay++;
        double monangle = face(getTarget(), canMove());
        if(ultCharge>=ult&&distanceTo(getTarget())<=ultrange){
            ult();
        }
        else if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else{
            if(canAttack()){
                if(ammo.hasAmmo()&&reloadDelay>=reloadTime)attack();
            }
        }
        if(life>0){
            life--;
            if(life==0){
                die(this);
            }
        }
    }
    public void attack(){
        explodeOn(attackrange, "enemy", (g)->{
            if(Math.abs(face(g, false)-getTargetRotation())<30){
                damage(g, 150);
            }
        }, null);
        ammo.useAmmo();
        reloadDelay = 0;
    }
    public void ult(){
        if(upgraded){
            heal(this, 200);
        }
        addObjectHere(new Roar(getRotation(), this));
        ultCharge = 0;
    }
    public int getDuration(){
        return life;
    }
    public int getMaxDuration(){
        return maxlife;
    }
    public void die(GridEntity source){
        if(cached!=null){
            addObjectHere(cached);
            cached.untrap();
            if(cached.getPercentHealth()<0.5){
                heal(cached, cached.getMaxHealth()/2-cached.getHealth());
            }
        }
        super.die(source);
    }
    public void notifyDamage(GridEntity target, int amt){
        ultCharge+=amt;
    }
    public String getPetID(){
        return "morphingstone-beast";
    }
}
