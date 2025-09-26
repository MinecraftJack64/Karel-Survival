package com.karel.game.gridobjects.gridentities.zombies.shaman;

import com.karel.game.GridEntity;
import com.karel.game.gridobjects.gridentities.zombies.SpawnableZombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Write a description of class DemonZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EvilSpiritZombie extends SpawnableZombie
{
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.pressurer};
    public String getStaticTextureURL(){return "spiritzareln.png";}
    private int damage = 50;
    /**
     * Initilise this rocket.
     */
    public EvilSpiritZombie()
    {
        this(50);
    }
    public EvilSpiritZombie(int health){
        super();
        scaleTexture(15, 15);
        setSpeed(4);
        startHealth(health);
    }
    public EvilSpiritZombie(GridEntity hive, int health){
        this(health);
        setTeam(hive.getTeam());
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>30)walk(monangle, 1);
        else{
            if(canAttack()){
                hit(damage, this);//TODO
            }
        }
        hit(1, this);
    }
    
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 0;
    }
    
    
    public String getName(){
        return "Evil Spirit Zombie";
    }
    @Override
    public String getZombieID(){
        return "evilspirit";
    }
}
