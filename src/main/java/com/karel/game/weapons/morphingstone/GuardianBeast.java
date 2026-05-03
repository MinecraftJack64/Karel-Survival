package com.karel.game.weapons.morphingstone;

import com.karel.game.Game;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.PercentageShield;
import com.karel.game.Pet;
import com.karel.game.Player;
import com.karel.game.Vector;
import com.karel.game.shields.ShieldID;
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
    private MorphingStone remote;
    private boolean controlled;
    private AmmoManager ammo = new AmmoManager(60, 3, 3);
    /**
     * Initilise this rocket.
     */
    public GuardianBeast(GridEntity hive, GridEntity cache, boolean upgraded, MorphingStone controller)
    {
        super(hive);
        inherit(hive);
        setImage("beast.png");
        scaleTexture(60);
        this.upgraded = upgraded;
        maxlife = life = upgraded?550:450;
        remote = controller;
        setSpeed(4);
        if(remote!=null){
            controlled = true;
            remote.setSpecialAmmo(ammo);
            setSpeed(remote.getHolder().getSpeed());
        }
        if(cache.trap()){
            cached = cache;
            cached.getWorld().removeObject(cached);
            startHealth(Math.min(cache.getMaxHealth()*2, 2000));
        }else{
            startHealth(800);
        }
        inherit(hive);
    }
    public void reset(){
        maxlife = life = 550;
        startCooldown = 45;
    }
    public int spawnImmunityLength(){
        return 0;
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
        if(controlled){
            remote.tick();
            remote.render();
            remote.getHolder().setLocation(getX(), getY());
            remote.setUltCharge((int)(remote.getUlt()*(ultCharge*1.0/ult)));
            if(controlled){
                remote.getHolder().setWorld(getWorld());
            }
            Vector v = ((Player)remote.getHolder()).getMovementControlVector(getWorld());
            walk(v.getDirection()+90, v.getLength());
            face(getWorld().getGridMouseX(), getWorld().getGridMouseY(), canMove());
            reloadDelay++;
            if(reloadTime <= reloadDelay&&(Game.isAttackDown()||Game.getInputMethod().equals("keyboard")&&Greenfoot.isActive("attack"))){
                attack();
            }
            if(Greenfoot.isActive("ult")&&ultCharge>=ult){
                ult();
            }
            if(Greenfoot.isActive("gadget")){
                remote.activateGadget();
            }
        }else{
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
        }
        ammo.reload(getReloadMultiplier());
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
        if(controlled){
            if(remote.getUltUpgrade()==1){
                applyShield(new PercentageShield(new ShieldID(this), 0.5, 90));
            }
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
            cached.setWorld(null);
            addObjectHere(cached);
            cached.untrap();
            if(cached.getPercentHealth()<0.5){
                heal(cached, cached.getMaxHealth()/2-cached.getHealth());
            }
        }
        if(controlled){
            remote.setSpecialAmmo(null);
            remote.dischargeUlt();
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
