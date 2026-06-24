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
    HashSet<GridEntity> targeted, hit;
    GridEntity added;
    public String getStaticTextureURL(){return "hornetzareln.png";}
    /**
     * Initilise this rocket.
     */
    public Weevil(HashSet<GridEntity> targeted, HashSet<GridEntity> hit, boolean isOriginal)
    {
        this.isOriginal = isOriginal;
        this.targeted = targeted;
        this.hit = hit;
        scaleTexture(20, 20);
        setSpeed(4.5);
        startHealth(isOriginal?120:40);
    }
    public Weevil(){
        this(new HashSet<GridEntity>(), new HashSet<GridEntity>(), true);
    }
    public void attack(){
        super.attack();
        getTarget().applyEffect(new PoisonEffect(50, 20, 3, this));
        if(added!=null){
            targeted.remove(added);
        }
        if(!hit.contains(getTarget()))getTarget().applyEffect(new InfectionEffect((g)->{
            for(int i = 0; i < 3; i++){
                g.addObjectHere(new Weevil(targeted, hit, false));
            }
        }, 60, this));
        hit.add(getTarget());
        kill(this);
    }
    public boolean isPotentialTarget(GridEntity g){
        return super.isPotentialTarget(g)&&(!targeted.contains(g)&&!hit.contains(g)||g==added);
    }
    public void behave(){
        super.behave();
        if(getTarget()!=added){
            if(targeted.contains(added)){
                targeted.remove(added);
                added = null;
            }
        }
        if(!targeted.contains(getTarget())){
            targeted.add(getTarget());
            added = getTarget();
        }
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
