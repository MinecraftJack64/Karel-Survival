package com.karel.game.gridobjects.gridentities.zombies.shaman;

import java.util.List;

import com.karel.game.GridEntity;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * A zombie that slows you when you are nearby. Occasionally sprays to poison you and boost other zombies.
 * 
 * @author MinecraftJack64
 */
public class ShamanZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.support, ZombieClass.spawner};
    private static final int gunReloadTime = 160;         // The minimum delay between firing the gun.
    private static final int passiveReloadTime = 4; 
    private double targetSpeed = 0;
    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    private int passivereloadDelayCount;
    public String getStaticTextureURL(){return "sporezareln.png";}
    public ShamanZombie()
    {
        reloadDelayCount = gunReloadTime;
        passivereloadDelayCount = passiveReloadTime;
        setSpeed(3);
        startHealth(600);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        reloadDelayCount+= getReloadMultiplier();
        passivereloadDelayCount++;
        double monangle = face(getTarget(), canMove());
        if(reloadDelayCount>=gunReloadTime&&canAttack()&&targetSpeed!=0){
            attack();
            reloadDelayCount = 0;
        }
        if(passivereloadDelayCount>=passiveReloadTime){
            passiveAttack();
            passivereloadDelayCount = 0;
        }
        if(targetSpeed==0){
            //Move towards nearest zombie
            walk(monangle, 1.5);
        }else{
            //Move towards enemy
            double speed = Math.min(targetSpeed/getSpeed(), 1);
            if(distanceTo(getTarget())>120)
                walk(monangle, speed);
        }
    }
    public boolean isPotentialTarget(GridEntity t){
        return super.isPotentialTarget(t)&&targetSpeed!=0||isAlliedWith(t)&&t!=this&&targetSpeed==0;
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 300;
    }

    public void attack(){
        addObjectHere(new ShamanWave(this));
        Sounds.play("sporespray");
    }
    //Calculate speeds
    public void passiveAttack(){
        List<GridEntity> l = getGEsInRange(200);
        double t = 0;
        int tn = 0;
        for(GridEntity ge: l){
            if(isAlliedWith(ge)&&ge.getMaxHealth()>=200){
                tn++;
                t+=ge.getMultipliedSpeed();
            }
        }
        if(t==0){
            targetSpeed = 0;
        }else{
            targetSpeed = t/tn;
        }
    }
    public String getName(){
        return "Shaman Zombie";
    }
    @Override
    public String getZombieID(){
        return "shaman";
    }
}
