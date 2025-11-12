package com.karel.game.gridobjects.gridentities.zombies.shaman;

import com.karel.game.EventListener;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.gridentities.zombies.SpawnableZombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Write a description of class DemonZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EvilSpiritZombie extends SpawnableZombie implements EventListener
{
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.pressurer};
    public String getStaticTextureURL(){return "spiritzareln.png";}
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
                getTarget().setMaxHealthLimit(50, new EffectID(this));
                getTarget().addEventListener(this);
                trap();
                getWorld().removeObject(this);
            }
        }
        hit(1, this);
    }
    public boolean on(String event, GridObject source){
        if(!event.equals("ult")&&!event.equals("die"))return false;
        source.addObjectHere(this);
        untrap();
        matchTeam(source);
        applyEffect(new SpeedPercentageEffect(1.2, Greenfoot.getRandomNumber(100), source));
        if(source instanceof GridEntity g){
            startHealth(g.getMaxHealth()-g.getHealth());
            g.clearMaxHealthLimit(new EffectID(this));
        }
        return true;
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
