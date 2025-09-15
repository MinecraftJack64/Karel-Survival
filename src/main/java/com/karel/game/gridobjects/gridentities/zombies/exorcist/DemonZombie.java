package com.karel.game.gridobjects.gridentities.zombies.exorcist;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.gridobjects.gridentities.zombies.SpawnableZombie;
import com.karel.game.gridobjects.gridentities.zombies.guardianangel.GuardianAngelZombie;

/**
 * Write a description of class DemonZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DemonZombie extends SpawnableZombie
{

    public String getStaticTextureURL(){return "demonzareln.png";}
    private GridEntity myhive;
    public DemonZombie(GridEntity hive)
    {
        this();
        myhive = hive;
        inherit(hive);
    }
    public DemonZombie(){
        scaleTexture(20, 20);
        setSpeed(4.5);
        startHealth(25);
    }
    public void behave()
    {
        if(getHeight()==0){
            setHeight(1);
        }
        super.behave();
        if(myhive!=null&&myhive.isDead()){
            heal(1, this);
        }
    }
    public void attack(){
        super.attack();
        getTarget().applyEffect(new PoisonEffect(2, 2, 2, this));
        GridObject t = getTarget();
        pullToBranch(t, t.getRotation()+90, distanceTo(t)-10);
        heal(this, 2);
    }
    
    public int defaultDamage(){
        return (int)(20+(40-distanceTo(getTarget()))/2);
    }
    public int defaultReloadTime(){
        return (int)(distanceTo(getTarget())/2+10);
    }
    public int defaultRange(){
        return 40;
    }
    
    public void hit(int amt, GridObject source){
        super.heal(amt, source);
    }
    public void heal(int amt, GridObject source){
        super.hit(amt, source);
    }
    
    @Override
    public int getXP(){
        return 0;
    }
    
    public boolean canFly(){
        return true;
    }

    public boolean isPotentialTarget(GridEntity e){
        return super.isPotentialTarget(e)||e instanceof GuardianAngelZombie;
    }
    
    public String getName(){
        return "Demon Zombie";
    }
}
