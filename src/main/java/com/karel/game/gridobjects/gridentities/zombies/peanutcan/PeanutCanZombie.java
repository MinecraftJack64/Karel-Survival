package com.karel.game.gridobjects.gridentities.zombies.peanutcan;

import com.karel.game.Greenfoot;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Write a description of class ExplodingZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PeanutCanZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.assault, ZombieClass.meatshield};
    public String getStaticTextureURL(){return "tntzareln.png";}
    public PeanutCanZombie()
    {
        setSpeed(5);
        startHealth(200);
    }
    public void behave()
    {
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>25)walk(monangle, 1);
        else{
            damage(this, 10); // canAttack not factored
        }
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 200;
    }
    @Override
    public boolean prioritizeTarget(){
        return true;
    }
    public void die(GridObject source){
        try{
            for(int i = 0; i < 5; i++){
                addObjectHere(new ZNutSnake(getRotation()+Greenfoot.getRandomNumber(60)-30, this, i*10+5));
            }
            super.die(source);
            playSound("Zombies/exploding/explode.wav");
        }catch(Exception e){
        }
    }
    public String getName(){
        return "Peanut Can Zombie";
    }
    @Override
    public String getZombieID(){
        return "peanutcan";
    }
}
