package com.karel.game;

import com.karel.game.ItemHolder;
import com.karel.game.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.weapons.fastfood.FastFood;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class ShooterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bot extends Pet
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.melee, ZombieClass.controller};

    public BotHand hand = new BotHand();
    private Weapon weapon = new FastFood(hand);

    public String getStaticTextureURL(){return "gunzareln.png";}
    private static double attackrange = 200;
    /**
     * Initilise this rocket.
     */
    public Bot(GridEntity source)
    {
        super(source);
        setSpeed(5);
        startHealth(300);
    }
    public void behave()
    {
        double monangle = face(getTarget(), canMove());
        if(canAttack()){
            weapon.tick();
        }
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else{
            fire();
            if(distanceTo(getTarget())>30){
                walk(monangle, 0.75);
            }
        }
        if(distanceTo(getTarget())>attackrange)weapon.ult();
    }
    public void fire() 
    {
        if (canAttack()){
            weapon.use();
        }
    }
    //ovveride this
    public int getXP(){
        return 400;
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    public BotHand getHand(){
        return hand;
    }
    public String getName(){
        return "Bot";
    }
    public class BotHand implements ItemHolder{
        //TODO
        @Override
        public double getTargetRotation(){
            return getHolder().getRotation();
        }
        public double getTargetX(){
            return getHolder().getTarget().getX();
        }
        public double getTargetY(){
            return getHolder().getTarget().getY();
        }
        public void setTargetLock(boolean t){
            //
        }
        public void setRotationLock(boolean t){
            //
        }
        public void setMovementLock(boolean t){
            //
        }
        public boolean isAttacking(){
            return distanceTo(getTarget())<=attackrange;
        }
        public boolean isMoving(){
            return distanceTo(getTarget())>30;
        }
        public double getReloadSpeed(){
            return getHolder().getReloadMultiplier();
        }
        public boolean isMainWeapon(){
            return false;
        }
        public Bot getHolder(){
            return Bot.this;
        }
    }
}
