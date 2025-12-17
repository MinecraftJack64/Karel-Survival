package com.karel.game.gridobjects.gridentities.zombies.president;

import com.karel.game.GridEntity;
import com.karel.game.PercentageShield;
import com.karel.game.Sounds;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.gridentities.zombies.SpawnableZombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.shields.ShieldID;

/**
 * Write a description of class BodyguardZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BodyguardZombie extends SpawnableZombie
{
    private ZombieClass[] classes = new ZombieClass[]{ZombieClass.tank};
    private static final int gunReloadTime = 50;

    private int reloadDelayCount;

    public String getStaticTextureURL(){return "bodyguardzareln.png";}
    private static double shootrange = 400, attackrange = 30, idlerange = 20;
    private double zoneX, zoneY;
    public int action = 1;//0 - idle next to owner, 1 - attack, 2 - assist
    PresidentZombie owner;
    public int id;
    private boolean hasowner;
    private boolean isfeasting;
    public BodyguardZombie()
    {
        reloadDelayCount = 5;
        setSpeed(3);
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
        if(action==0){
            monangle = owner.getRotation();
            if(distanceTo(zoneX, zoneY)>=idlerange){
                walk(face(zoneX, zoneY, false), 1);
            }
        }
        else if(action!=0)monangle = face(getTarget(), canMove());
        else monangle = 0;
        reloadDelayCount++;
        if(action==1){
            if(hasowner){
                if(monangle-owner.getAngle(getTarget().getX(), getTarget().getY())-90<-5){
                    walk(monangle-90, 1);
                }else if(monangle-owner.getAngle(getTarget().getX(), getTarget().getY())-90>5){
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
        }
    }
    public void feast(){
        isfeasting = true;
        super.feast();
    }
    public void enrage(){
        this.applyShield(new PercentageShield(new ShieldID(this), 0.15, -1));
        this.applyEffect(new SpeedPercentageEffect(1.2, 1000, this));
        this.setPower(1.2);
    }
    public void setPatrolZone(double x, double y){
        zoneX = x;
        zoneY = y;
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
            ZDefensive bullet = new ZDefensive (getRotation(), this);
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 0;
    }
    
    public String getName(){
        return "Bodyguard Zombie";
    }
    @Override
    public String getZombieID(){
        return "bodyguard";
    }
}
