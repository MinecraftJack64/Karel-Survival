package com.karel.game;
import java.util.List;

import com.karel.game.weapons.ShieldID;

/**
 * Write a description of class BodyguardZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BodyguardZombie extends SpawnableZombie
{
    private static final int gunReloadTime = 50;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "bodyguardzareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private static double shootrange = 400, attackrange = 30, retreatrange = 100, idlerange = 20;
    private int enraged = 0;
    public int action = 1;//0 - idle next to owner, 1 - attack, 2 - assist
    PresidentZombie owner;
    public int id;
    private boolean hasowner;
    private boolean isfeasting;
    /**
     * Initilise this rocket.
     */
    public BodyguardZombie()
    {
        reloadDelayCount = 5;
        setSpeed(4);
        startHealth(450);
    }
    public BodyguardZombie(PresidentZombie owner, int bgid)
    {
        this();
        action = 0;
        this.owner = owner;
        this.id = bgid;
        hasowner = true;
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        isfeasting = false;
        if(hasowner&&owner.isDead()){
            enrage();
            hasowner = false;
        }
        if(getTarget()==null&&hasowner){
            action = 0;
        }else if(isAlliedWith(getTarget())){
            action = 2;
        }else{
            action = 1;
        }
        double monangle;
        if(action==0&&distanceTo(owner)<=idlerange)monangle = owner.getRealRotation();
        else if(action!=0)monangle = face(getTarget(), canMove());//temp
        else monangle = 0;
        //setRotation(getRotation()-1);
        reloadDelayCount++;
        if(action==1){
            if(hasowner){
                if(monangle-owner.getAngle(getTarget().getRealX(), getTarget().getRealY())-90<-5){
                    walk(monangle-90, 1);
                }else if(monangle-owner.getAngle(getTarget().getRealX(), getTarget().getRealY())-90>5){
                    walk(monangle+90, 1);
                }
            }
            if(distanceTo(getTarget())>shootrange)walk(monangle, 0.6);
            else{
                if(distanceTo(getTarget())>attackrange){
                    walk(monangle, 0.9);
                    fire();
                }else{
                    if(canAttack())attack();
                }
            }
        }else if(action==2){
            if(distanceTo(getTarget())>attackrange)walk(monangle, 1.25);
            else{
                heal(getTarget(), 5);
            }
        }else{
            /*if(distanceTo(getTarget())>idlerange)walk(monangle, 1);
            else{
                //stay near owner
            }*/
        }
    }
    public void feast(){
        isfeasting = true;
        super.feast();
    }
    public void enrage(){
        this.applyShield(new PercentageShield(new ShieldID(this), 0.15, -1));
        this.applyEffect(new SpeedPercentageEffect(1.2, 1000, this));
        this.setPower(1.5);
        enraged = 1;
    }
    public void attack(){
        damage(getTarget(), 20);
    }
    public GridEntity getTarget(){
        if(hasowner&&!isfeasting){
            return owner.getTarget(id);
        }else{
            return super.getTarget();
        }
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZDefensive bullet = new ZDefensive (getRealRotation(), this);
            getWorld().addObject (bullet, getRealX(), getRealY());
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    //ovveride this
    public int getXP(){
        return 0;
    }
    
    public String getName(){
        return "Bodyguard Zombie";
    }
}
