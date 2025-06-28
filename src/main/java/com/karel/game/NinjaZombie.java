package com.karel.game;
import java.util.List;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;

/**
 * Write a description of class NinjaZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NinjaZombie extends Zombie
{
    public String getStaticTextureURL(){return "ninjazareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int phase = 0;//0 - waiting, 1 - charging, 2 - attacking, 3 - retreating
    private int phasecooldown = 0;//0 - (75-150), 1 - none, 2 - 135(turn 8 deg each time)
    private double homex, homey;
    private int inviscooldown = 40;//10 or 50-100 invisible, 20-40 visible
    private boolean isinvis = false;
    private GridEntity target;
    private double lastdegtotarget;
    private static double attackrange = 80;
    /**
     * Initilise this rocket.
     */
    public NinjaZombie()
    {
        phasecooldown = Greenfoot.getRandomNumber(75)+75;
        setSpeed(5);
        startHealth(150, false);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        double targang = phase<3?face(getTarget(), canMove()):face(homex, homey,  canMove());
        if(phase==1){
            walk(targang, 0.85);
            if(distanceTo(getTarget())<attackrange){
                phase++;
                lastdegtotarget = targang+90;
                target = getTarget();
                phasecooldown = 90;
            }
        }else if(phase==3){
            walk(targang, 1);
            if(distanceTo(homex, homey)<=5){
                phase = 0;
                phasecooldown = Greenfoot.getRandomNumber(75)+75;
                setLocation(homex, homey);
            }
        }else{
            if(phase==2){
                if(canMove()&&distanceTo(target)<attackrange+10&&!target.hasMounter()){
                    setLocation((attackrange*Math.cos(lastdegtotarget*Math.PI/180)+target.getX()), (attackrange*Math.sin(lastdegtotarget*Math.PI/180)+target.getY()));
                    lastdegtotarget+=getMultipliedSpeed()*1.6;
                }else{
                    phasecooldown = 0;//retreat when stunned/knockbacked or target is too far away to catch up to.
                }
                if(canAttack()){
                    if(phasecooldown%15==0){
                        if(isAggroTowards(target)){//make sure i'm not hypnotized
                            fire();
                        }else{
                            phasecooldown = 0;
                        }
                    }
                }
            }
            phasecooldown--;
            if(phasecooldown<=0){
                if(phase==0){
                    homex = getX();
                    homey = getY();
                }
                phase++;
            }
        }
        //setRotation(getRotation()-1);
        if(inviscooldown<=0){
            isinvis = !isinvis;
            if(isinvis){
                inviscooldown = Greenfoot.getRandomNumber(50)+50;
            }else{
                inviscooldown = 40+Greenfoot.getRandomNumber(40);
            }
        }
        //inviscooldown = 1000;
        if(distanceTo(getTarget())<90){
            isinvis = false;
        }
        if(!isinvis&&getOpacity()<255){
            setOpacity(getOpacity()+51);
        }else if(isinvis&&getOpacity()>0){
            setOpacity(getOpacity()-51);
        }
        inviscooldown--;
    }
    @Override
    public boolean canDetect(){
        return getOpacity()>0;
    }
    public void feast(){
        if(getOpacity()<255){
            setOpacity(getOpacity()+51);
        }
        super.feast();
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        Shuriken bullet = new Shuriken (getRotation(), this);
        getWorld().addObject (bullet, getX(), getY());
        //bullet.move ();
    }
    //ovveride this
    public int getXP(){
        return 600;
    }
    public void damage(int amt){
        super.damage(amt);
        isinvis = false;
        inviscooldown = 10;
    }
    @Override
    public boolean prioritizeTarget(){
        return getNearestTarget()!=null&&distanceTo(getNearestTarget())>=90;
    }
    
    public String getName(){
        return "Ninja Zombie";
    }
}
