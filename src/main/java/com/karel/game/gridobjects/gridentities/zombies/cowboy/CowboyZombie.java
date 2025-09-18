package com.karel.game.gridobjects.gridentities.zombies.cowboy;

import com.karel.game.ItemHolder;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.weapons.shotgun.Shotgun;

/**
 * Write a description of class ShooterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CowboyZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.melee, ZombieClass.controller};

    public CowboyZombieHand hand = new CowboyZombieHand();
    private Shotgun weapon = new Shotgun(hand){
        @Override
        public int getUlt(){
            return 1200; // takes longer to get its ult
        }
    };

    public String getStaticTextureURL(){return "gunzareln.png";}
    private static double attackrange = 200;
    /**
     * Initilise this rocket.
     */
    public CowboyZombie()
    {
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
    public CowboyZombieHand getHand(){
        return hand;
    }
    public String getName(){
        return "Cowboy Zombie";
    }
    @Override
    public String getZombieID(){
        return "cowboy";
    }
    public class CowboyZombieHand implements ItemHolder{
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
        public CowboyZombie getHolder(){
            return CowboyZombie.this;
        }
    }
}
