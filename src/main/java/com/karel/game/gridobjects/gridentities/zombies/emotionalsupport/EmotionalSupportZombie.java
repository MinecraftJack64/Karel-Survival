package com.karel.game.gridobjects.gridentities.zombies.emotionalsupport;

import java.util.HashSet;

import com.karel.game.GridEntity;
import com.karel.game.effects.PowerPercentageEffect;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Does not move, indicates that killing it will result in the boss spawning. Takes on the appearance of the boss
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EmotionalSupportZombie extends Zombie
{
    HashSet<GridEntity> needies = new HashSet<GridEntity>(), all = new HashSet<GridEntity>();
    GridEntity target;
    GridEntity prevTarget;
    private int searchPhase;
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.support};
    public String getStaticTextureURL(){
        return "heraldzareln.png";
    }
    public EmotionalSupportZombie()
    {
        setSpeed(5);
        startHealth(250);
    }
    public void behave(){
        emitAura();
        super.behave();
    }
    public int defaultReloadTime(){
        return 30;
    }
    public int defaultRange(){
        return 50;
    }
    public void attack(){
        GridEntity g = getTarget();
        if(isAggroTowards(g)){
            damage(g, 40);
            g.applyEffect(new PowerPercentageEffect(0.8, 80, this));
            g.applyEffect(new SpeedPercentageEffect(0.8, 80, this));
        }else if(isAlliedWith(g)){
            if(g.getHealth()<g.getMaxHealth()){
                heal(g, prevTarget!=g?100:30);
                needies.add(g);
            }else{
                all.add(g);
            }
            g.applyEffect(new PowerPercentageEffect(1.2, 80, this));
            g.applyEffect(new SpeedPercentageEffect(1.2, 80, this));
        }
        target = null;
        prevTarget = g;
    }
    public void emitAura(){
        explodeOn(150, (e)->{
            if(isAlliedWith(e)){
                heal(e, 3);
            }else if(isAggroTowards(e)){
                damage(e, 3);
            }
        });
    }
    public GridEntity getTarget(){
        if(target!=null){
            return target;
        }
        searchPhase = 0;
        GridEntity res = super.getNearestTarget();
        if(res==null){
            needies.clear();
            searchPhase = 1;
            res = super.getNearestTarget();
            if(res==null){
                all.clear();
                return super.getTarget();
            }
        }
        return res;
    }
    public boolean isPotentialTarget(GridEntity g){
        return g!=this&&g.canDetect()&&(searchPhase==0?isAlliedWith(g)&&!needies.contains(g)&&g.getPercentHealth()<1:(searchPhase==1?isAlliedWith(g)&&!all.contains(g):super.isPotentialTarget(g)));
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 100;
    }
    public String getName(){
        return "Emotional Support Zombie";
    }
    @Override
    public String getZombieID(){
        return "emotionalsupport";
    }
}
