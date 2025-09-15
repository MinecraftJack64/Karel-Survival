package com.karel.game.gridobjects.gridentities.zombies.fungal;

import com.karel.game.GridEntity;
import com.karel.game.Sounds;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * A zombie that slows you when you are nearby. Occasionally sprays to poison you and boost other zombies.
 * 
 * @author MinecraftJack64
 */
public class FungalZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.controller, ZombieClass.tank, ZombieClass.support};
    private static final int gunReloadTime = 120;         // The minimum delay between firing the gun.
    private static final int passiveReloadTime = 4; 
    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    private int passivereloadDelayCount;
    public String getStaticTextureURL(){return "sporezareln.png";}
    public FungalZombie()
    {
        reloadDelayCount = gunReloadTime;
        passivereloadDelayCount = passiveReloadTime;
        setSpeed(0.5);
        startHealth(1700);
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
        if(reloadDelayCount>=gunReloadTime&&canAttack()){
            attack();
            reloadDelayCount = 0;
        }
        if(passivereloadDelayCount>=passiveReloadTime){
            passiveattack();
            passivereloadDelayCount = 0;
        }
        if(distanceTo(getTarget())>150)
            walk(monangle, 1);
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 750;
    }

    public void attack(){
        //explode, works when stunned
        explodeOn(225, (g)->{
            if(isAlliedWith(g)){//
                //g.applyeffect(new DamagePercentageEffect(1.5, 120));
                g.applyEffect(new SpeedPercentageEffect(1.5, 120, this, new EffectID(this, "speedboost")));
            }
        }, null);//TODO replace with better explosion later
        addObjectHere(new FungalWave(this));
        Sounds.play("sporespray");
    }
    public void notifyHit(GridEntity g){
        g.applyEffect(new PoisonEffect((int)(75+(225-distanceTo(g))), 30, 4, this));
    }
    public void passiveattack(){
        explodeOn(250, "enemy", (g)->{g.applyEffect(new SpeedPercentageEffect(distanceTo(g)/400+0.3, 4, this, new EffectID(this, "slow")));}, null);
    }
    public String getName(){
        return "Fungal Zombie";
    }
}
