package com.karel.game.gridobjects.gridentities.zombies.marksman;

import com.karel.game.Greenfoot;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.firingsquad.FiringSquadZombie;
import com.karel.game.particles.SniperCasing;

/**
 * Write a description of class MarksmanZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MarksmanZombie extends Zombie
{
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.ranger};
    private static final int gunReloadTime = 300;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.

    private FiringSquadZombie commander;
    private double zoneX, zoneY;
    public String getStaticTextureURL(){return "riflezareln.png";}
    private static double attackrange = 500, retreatrange = 450;
    public MarksmanZombie()
    {
        reloadDelayCount = 5;
        setSpeed(3);
        startHealth(150);
    }
    public MarksmanZombie(FiringSquadZombie c){
        this();
        commander = c;
    }
    public void behave()
    {
        reloadDelayCount+=getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        if(commander!=null){
            if(commander.isDead()){
                commander = null;
            }else{
                if(distanceTo(zoneX, zoneY)>=getSpeed()){
                    face(zoneX, zoneY, true);
                    walk(getRotation(), 1);
                }
                else setRotation(commander.getRotation());
            }
        }else{
            if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
            else if(distanceTo(getTarget())<retreatrange){fire();walk(monangle, -0.75);}
            else{
                reloadDelayCount+=getReloadMultiplier();
                fire();
            }
        }
    }
    public void setZone(double x, double y){
        zoneX = x;
        zoneY = y;
    }
    public void commandFire(){
        reloadDelayCount = gunReloadTime;
        fire();
    }
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZSBullet bullet = new ZSBullet (getRotation(), this);
            getWorld().addObject (bullet, getX(), getY());
            addObjectHere(new SniperCasing(Greenfoot.getRandomNumber(360)));
            Sounds.play("marksmanshoot");
            reloadDelayCount = 0;
        }
    }
    public int getXP(){
        return 500;
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Marksman Zombie";
    }
    @Override
    public String getZombieID(){
        return "marksman";
    }
}
