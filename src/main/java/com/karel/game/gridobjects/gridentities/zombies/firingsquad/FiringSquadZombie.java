package com.karel.game.gridobjects.gridentities.zombies.firingsquad;

import com.karel.game.Sounds;
import com.karel.game.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.marksman.*;

/**
 * A marksman zombie that spawns other marksmen to its flanks and commands them.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FiringSquadZombie extends Zombie
{
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.leader, ZombieClass.spawner, ZombieClass.ranger};
    private static final int gunReloadTime = 500;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.

    private MarksmanZombie[] marksmen;

    public String getStaticTextureURL(){return "rifleleaderzareln.png";}
    private static double attackrange = 500, retreatrange = 450;
    public FiringSquadZombie()
    {
        reloadDelayCount = 300;
        setSpeed(2);
        startHealth(300);
        scaleTexture(55);
    }
    public void behave()
    {
        reloadDelayCount+=getReloadMultiplier();
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>attackrange){
            walk(monangle, 1);
            updateZones();
        }
        else if(distanceTo(getTarget())<retreatrange){fire();}
        else{
            reloadDelayCount+=getReloadMultiplier();
            fire();
        }
    }

    private void fire() 
    {
        if(reloadDelayCount>=gunReloadTime&&marksmen==null){
            marksmen = new MarksmanZombie[6];
            for(int i = 0; i < marksmen.length; i++){
                marksmen[i] = new MarksmanZombie(this);
                addObjectHere(marksmen[i]);
            }
            updateZones();
            reloadDelayCount = 250;
        }
        else if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZSBullet bullet = new ZSBullet (getRotation(), this);
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("marksmanshoot");
            reloadDelayCount = 0;
            for(MarksmanZombie z: marksmen){
                if(!z.isDead())z.commandFire();
            }
        }
    }
    public void updateZones(){
        if(marksmen!=null)
        {
            int id = 0;
            for(MarksmanZombie z: marksmen){
                if(!z.isDead())z.setZone(calculateZoneX(id), calculateZoneY(id));
                id++;
            }
        }
    }
    public double calculateZoneX(int id){
        return getBranchX(getRotation()+(id<3?0:180), 60*(id%3+1))+getX();
    }
    public double calculateZoneY(int id){
        return getBranchY(getRotation()+(id<3?0:180), 60*(id%3+1))+getY();
    }
    public int getXP(){
        return 500;
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Firing Squad Zombie";
    }
}
