package com.karel.game.gridobjects.gridentities.zombies.tree;

import java.util.HashSet;

import com.karel.game.GridEntity;
import com.karel.game.effects.InfectionEffect;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.gridobjects.gridentities.zombies.SpawnableZombie;

/**
 * Write a description of class Hornet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Weevil extends SpawnableZombie
{
    boolean isOriginal = false;
    HashSet<GridEntity> targeted;
    public String getStaticTextureURL(){return "hornetzareln.png";}
    /**
     * Initilise this rocket.
     */
    public Weevil(HashSet<GridEntity> targeted, boolean isOriginal)
    {
        this.isOriginal = isOriginal;
        this.targeted = targeted;
        scaleTexture(20, 20);
        setSpeed(4.5);
        startHealth(isOriginal?120:40);
    }
    public Weevil(){
        this(new HashSet<GridEntity>(), true);
    }
    public void attack(){
        super.attack();
        getTarget().applyEffect(new PoisonEffect(50, 20, 3, this));
        if(!targeted.contains(getTarget()))getTarget().applyEffect(new InfectionEffect((g)->{
            for(int i = 0; i < 3; i++){
                g.addObjectHere(new Weevil(targeted, false));
            }
        }, 60, this));
        targeted.add(getTarget());
        kill(this);
    }
    public boolean isPotentialTarget(GridEntity g){
        return super.isPotentialTarget(g)&&!targeted.contains(g);
    }
    public void behave(){
        super.behave();
        if(isOriginal){
            hit(2, this);
        }
    }
    
    //ovveride this
    public int getXP(){
        return 5;
    }
    
    public String getName(){
        return "Zombie Weevil";
    }
    @Override
    public String getZombieID(){
        return "weevil";
    }
}
