package com.karel.game.gridobjects.gridentities.zombies.beermartyr;

import com.karel.game.Greenfoot;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Does not move, indicates that killing it will result in the boss spawning. Takes on the appearance of the boss
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BeerMartyrZombie extends Zombie
{
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.support, ZombieClass.meatshield};
    private static final int reloadTime = 150;
    private int ammo = 0;
    private int strafecooldown = 0;
    private boolean cstrafedir = true;
    public String getStaticTextureURL(){
        return "heraldzareln.png";
    }
    public BeerMartyrZombie()
    {
        setSpeed(3);
        startHealth(200);
    }
    public void behave(){
        ammo++; // not affected by reload speed
        if(ammo>=reloadTime){
            fire();
        }
        double monangle = face(getTarget(), canMove());
        strafecooldown--;
        if(strafecooldown<=0){
            cstrafedir = !cstrafedir;
            strafecooldown = Greenfoot.getRandomNumber(40)+20;
        }
        if(cstrafedir){
            walk(monangle+90, 0.8);
        }else{
            walk(monangle-90, 0.8);
        }
        super.behave();
    }
    public void die(GridObject source){
        addObjectHere(new BeerPuddle(this));
        super.die(source);
    }
    private void fire(){
        if(canAttack()){
            ammo = 0;
            ZBeerBreath bullet = new ZBeerBreath(getRotation(), this);
            addObjectHere(bullet);
        }
    }
    public int defaultDamage(){
        return 100;
    }
    public int defaultReloadTime(){
        return 30;
    }
    public int defaultRange(){
        return 50;
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 0;
    }
    public String getName(){
        return "Beer Martyr Zombie";
    }
    @Override
    public String getZombieID(){
        return "beermartyr";
    }
}
